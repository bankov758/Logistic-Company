import { category, column, item } from "@/components/home/EmployeeInterface/Table";

export const tableColumns: column[] = [
    {
        title: "Departure place",
        code: "departure",
        id: 1,
    },
    {
        title: "Arrival place",
        code: "arrival",
        id: 2,
    },
    {
        title: "Sender name",
        code: "sender",
        id: 3,
    },
    {
        title: "Receiver name",
        code: "receiver",
        id: 4,
    },
    {
        title: "Status",
        code: "status",
        id: 5,
    },
    {
        title: "Actions",
        code: "actions",
        hide: true,
        id: 6,
    },
];

export const categories: category[] = [
    {
        title: "Registered",
        code: "registered",
        id: 1,
    },
    {
        title: "Sent",
        code: "sent",
        id: 2,
    },
    {
        title: "Received",
        code: "received",
        id: 3,
    },
];

export const data: item[] = [
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "registered",
        id: 1,
    },
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "registered",
        id: 2,
    },
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "registered",
        id: 3,
    },
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "sent",
        id: 4,
    },
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "sent",
        id: 5,
    },
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "sent",
        id: 6,
    },
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "received",
        id: 7,
    },
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "received",
        id: 8,
    },
    {
        departure: "Sofia",
        arrival: "sofia",
        sender: "Antoan",
        receiver: "Beti",
        status: "Active",
        category: "received",
        id: 9,
    },
];