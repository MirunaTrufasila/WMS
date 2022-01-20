angular.module('lite')
    .constant('APP_MENU',
        [
            {
                name: 'admin',
                label: 'general.mainMenu.ADMIN',
                sections: [
                    {
                        title: 'general.menu.admin.system.SECTION_TITLE',
                        submenus: [
                            {
                                url: 'users',
                                label: 'users.TITLE'
                            },
                            {
                                url: 'employees-reduction',
                                label: 'employeesReduction.TITLE'
                            },
                        ]
                    }
                ]
            }
        ]
    );