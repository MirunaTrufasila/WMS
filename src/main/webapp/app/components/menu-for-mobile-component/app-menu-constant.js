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
                                url: 'company-details',
                                label: 'firma.TITLE'
                            },
                            {
                                url: 'facturi',
                                label: 'facturi.TITLE'
                            },
                            {
                                url: 'employees',
                                label: 'angajati.TITLE'
                            },
                            {
                                url: 'furnizori',
                                label: 'furnizori.TITLE'
                            },
                            {
                                url: 'activities',
                                label: 'general.menu.admin.system.ACTIVITIES_MANAGEMENT'
                            }
                        ]
                    }
                ]
            },
        ]
    );