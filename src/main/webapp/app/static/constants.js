HEADER_FILE_NAME = 'serverFileName';
HEADER_ERROR_MESSAGE = 'errorMessage';
RELOAD_CURRENCIES = "RELOAD_CURRENCIES";
DO_FILE_VALIDATION = "DO_FILE_VALIDATION";
SEND_TO_CUSTOMER_PROGRAM = "SEND_TO_CUSTOMER_PROGRAM";
SEND_TO_CUSTOMER_EXPEDITION_PROGRAM = "SEND_TO_CUSTOMER_EXPEDITION_PROGRAM";
SEND_TO_SUPPLIER = "SEND_TO_SUPPLIER";
USER_NOTIFICATION = "USER_NOTIFICATION";
DATETIME_FORMATS = {
    DATE_TIME_EU: "DD/MM/YYYY HH:mm:ss",
    DATE_TIME_ISO: "YYYYMMDDHHmmss",
    DATE_EU: "DD/MM/YYYY",
    DATE_WEEKDAY: "DD/MM/YYYY ddd",
    TIME: "HH:mm:ss"
};

/**
 * Initialize the multi-selection for a filter of type selectbox.
 * @param list the select options
 * @param selected the actual selection
 * @returns {*} the filtered result
 */
var selectMultipleItemsDropDown = function (list, selected) {
    if (selected && Array.isArray(selected)) {
        return list.filter(function (item) {
            return selected.some(function (dataItem) {
                return item.value === dataItem;
            });
        });
    }
    return [];
};

/**
 * Initializes the selection for a single-selection driopbox
 * @param list the dropbox datasource elements
 * @param selected the selected filters, as list of values
 * @returns {*} the <key,value> options from data source
 */
var selectSingleItemInDropdown = function (list, selected) {
    if (selected) {
        return list.filter(function (item) {
            return item.value === selected;
        });
    }
    return [];
};

/**
 * Extracts the selection from combo and triggers the server filter() method
 * @param selected the items which are currently selected
 * @returns {*} the keys as list (e.g. list of integers)
 */
var parseMultipleDropdownSelection = function (selected) {
    if (selected && Array.isArray(selected)) {
        return selected.map(function (a) {
            return a.value;
        });
    }
    return [];
};

var parseMultipleDropdownSelectionForProperty = function (selected, property) {
    if (selected && Array.isArray(selected)) {
        return selected.map(function (a) {
            return a[property];
        });
    }
    return [];
};

var createOptionsListForDropDown = function (items) {
    var options = [];
    for (var index in items) {
        options.push({
            value: items[index],
            label: items[index]
        });
    }
    return options;
};

var createOptionsListForDropDownForProperty = function (items, property) {
    var options = [];
    for (var index in items) {
        if (options.filter(function (item) {
            return item.value == items[index][property];
        }).length === 0) {
            options.push({
                value: items[index][property],
                label: items[index][property]
            });
        }
    }
    return options;
};

var createOptionsListForDropDownForFunction = function (items, callbackFcn) {
    var options = [];
    for (var index in items) {
        if (options.filter(function (item) {
            return item.value == items[index].id;
        }).length === 0) {
            options.push({
                value: items[index].id,
                label: callbackFcn(items[index])
            });
        }
    }
    return options;
};

var createOptionsListForDropDownFromMap = function (items) {
    var options = [];
    for (var prop in items) {
        options.push({value: prop, label: items[prop]});
    }
    return options;
};

var createOptionsListForMultiSelectFromMap = function (items) {
    var options = [];
    for (var prop in items) {
        options.push({id: prop, code: items[prop]});
    }
    return options;
};

/**
 * The same thing as above, for single selection combos
 * @param selected
 * @returns {*}
 */
var parseSingleDropdownSelection = function (selected) {
    if (Array.isArray(selected)) {
        if (selected[0]) {
            return selected[0].value;
        }
    } else if (selected) {
        return selected.value;
    }
    return null;
};

/**
 * Gets a valid date or null from the specified argument
 * @param arg the presumed date
 * @returns {*}
 */
var convertToDate = function (arg) {
    try {
        if (arg == null) {
            return null;
        }
        var dateMin = new Date(arg);
        if (moment(dateMin).isValid()) {
            return dateMin;
        }
    } catch (e) {
        console.log("Wrong date arg in convertToDate function " + arg);
        return null;
    }
};

/**
 * Methods is able to compare a string with a date by converting string into date first, if necessary
 * @param date1 first date, as date, string or whatever
 * @param date2 second date
 * @returns {boolean} true or false if values are equal
 */
var compareDates = function (date1, date2) {
    if (date1 == null && date2 == null) {
        return true;
    }

    if (date1 == null || date2 == null) {
        return false;
    }

    if (typeof date1 === 'string') {
        date1 = convertToDate(date1);
    }
    if (typeof date2 === 'string') {
        date2 = convertToDate(date2);
    }
    return date2.getTime() === date1.getTime();
};

/**
 *
 * @param dateArg
 * @returns {*}
 */
var formatDate = function (dateArg) {
    if (typeof dateArg === 'object' && dateArg !== null) {
        var momentInTime = moment(dateArg);
        if (momentInTime.isValid()) {
            return momentInTime.format('YYYY-MM-DD HH:mm:ss');
        }
        return null;
    }
    return dateArg;
};

var formatDateWithTimeAdded = function (dateArg, min) {
    if (typeof dateArg === 'object' && dateArg !== null) {
        var minDate = new Date(dateArg.getFullYear(), dateArg.getMonth(), dateArg.getDate(), 0, 0, 0);
        var maxDate = new Date(dateArg.getFullYear(), dateArg.getMonth(), dateArg.getDate(), 23, 59, 59);
        var momentInTime = moment(min === true ? minDate : maxDate);
        if (momentInTime.isValid()) {
            return momentInTime.format('YYYY-MM-DD HH:mm:ss');
        }
        return null;
    }
    return dateArg;
};

var isEmpty = function (arg) {
    if (arg == null || typeof arg === undefined) {
        return true;
    }
    return arg.length === 0;
};

var stringFilterChanged = function (fromEnter, filter, term) {
    if (fromEnter === true && filter !== term) {
        return true;
    }
    return isEmpty(term) && !isEmpty(filter);
};

var getIconByTransportCode = function (transportType) {
    var transportIcon = "";
    if (transportType.code == 10) {
        transportIcon = " fas fa-ship";
    } else if (transportType.code == 20) {
        transportIcon = "fas fa-train";
    } else if (transportType.code == 30) {
        transportIcon = " fas fa-truck-moving";
    } else if (transportType.code == 40) {
        transportIcon = " fas fa-plane";
    }
    return transportIcon;
};

var getFormattedDate = function (date) {
    let year = date.getFullYear();
    let month = (1 + date.getMonth()).toString().padStart(2, '0');
    let day = date.getDate().toString().padStart(2, '0');

    return day + '/' + month + '/' + year;
};