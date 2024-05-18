import { category, column } from "@/components/home/Table";

export const adminColumns: column[][] = [
    [//client column
        {
            title: "First name",
            code: "firstName",
            id: 1,
        },
        {
            title: "Last name",
            code: "lastName",
            id: 2,
        },
        {
            title: "Actions",
            code: "adminInterfaceActions",
            hide: true,
            id: 3,
        }
    ],
    [//employee column
        {
            title: "First name",
            code: "firstName",
            id: 1,
        },
        {
            title: "Last name",
            code: "lastName",
            id: 2,
        },
        {
            title: "Office",
            code: "officeAddress",
            id: 3,
        },
        {
            title: "Actions",
            code: "adminInterfaceActions",
            hide: true,
            id: 4,
        }
    ],
    [//courier column
        {
            title: "First name",
            code: "firstName",
            id: 1,
        },
        {
            title: "Last name",
            code: "lastName",
            id: 2,
        },
        {
            title: "Actions",
            code: "adminInterfaceActions",
            hide: true,
            id: 3,
        }
    ],
    [//office column
        {
            title: "Address",
            code: "address",
            id: 1,
        },
        {
            title: "Actions",
            code: "adminInterfaceActions",
            hide: true,
            id: 2,
        },
    ],
]

export const adminCategories: category[][] = [
    [//client categories
        {
            title: "Clients",
            code: "clients",
            id: 1,
        },
    ],
    [//employee categories
        {
            title: "Employees",
            code: "employees",
            id: 1,
        },
    ],
    [//courier categories
        {
            title: "Courier",
            code: "couriers",
            id: 1,
        },
    ],
    [//offices categories
        {
            title: 'Offices',
            code: 'offices',
            id: 1
        }
    ],
];