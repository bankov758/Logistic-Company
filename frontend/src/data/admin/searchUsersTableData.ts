import { category, column } from "@/components/home/Table";

export const searchColumns: column[] = [//clients
    {
        title: "Username",
        code: "username",
        id: 1,
    },
    {
        title: "First name",
        code: "firstName",
        id: 2,
    },
    {
        title: "Last name",
        code: "lastName",
        id: 3,
    },
    {
        title: "Actions",
        code: "adminInterfaceActions",
        hide: true,
        id: 4,
    }
]


export const searchCategories: category[] = [//client categories
    {
        title: "Users",
        code: "clients",
        id: 1,
    },
];