angular
    .module('lite')
    .constant('APP_PAGES',
        [
            {
                name: 'nomenclatures',
                label: 'general.mainMenu.NOMENCLATURES',
                sections: [
                    {
                        title: 'general.menu.admin.system.SECTION_TITLE',
                        submenus: [
                            {
                                url: 'users',
                                label: 'users.TITLE'
                            }
                        ]
                    }
                ]
            }
        ]
    );