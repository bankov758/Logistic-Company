import React from "react";

const Table: React.FC = () => {

    return (
        <div className="px-8 py-2 min-w-full">
            <table className="min-w-full">
                <thead className='bg-white'>
                    <tr className='text-sm font-semibold text-black'>
                        <th className="text-left px-4 py-3" scope='col'>
                            Departure place
                        </th>
                        <th className="text-left px-4 py-3" scope='col'>
                            Arrival place
                        </th>
                        <th className="text-left px-4 py-3" scope='col'>
                            Sender name
                        </th>
                        <th className="text-left px-4 py-3" scope='col'>
                            Receiver name
                        </th>
                        <th className="text-left px-4 py-3" scope='col'>
                            Status
                        </th>
                        <th className="text-left px-4 py-3 relative">
                            <span className='accessibility'>Edit</span>
                        </th>
                    </tr>
                </thead>
                <tbody className="bg-white">
                    <tr className="border-t text-sm font-semibold text-black">
                        <th 
                            className="bg-[#FAF9FB] px-3 py-2 text-left" 
                            colSpan={6}
                            scope="colgroup"
                        >
                            Received
                        </th>
                    </tr>
                    <tr className="border-t text-sm font-semibold text-black">
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                        <td className='px-3 py-2'>
                            Plovdiv
                        </td>
                        <td className='px-3 py-2'>
                            Antoan
                        </td>
                        <td className='px-3 py-2'>
                            Beti
                        </td>
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                    </tr>
                    <tr className="border-t text-sm font-semibold text-black">
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                        <td className='px-3 py-2'>
                            Plovdiv
                        </td>
                        <td className='px-3 py-2'>
                            Antoan
                        </td>
                        <td className='px-3 py-2'>
                            Beti
                        </td>
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                    </tr>
                    <tr className="border-t text-sm font-semibold text-black">
                        <th 
                            className="bg-[#FAF9FB] px-3 py-2 text-left" 
                            colSpan={6}
                            scope="colgroup"
                        >
                            Sent
                        </th>
                    </tr>
                    <tr className="border-t text-sm font-semibold text-black">
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                        <td className='px-3 py-2'>
                            Plovdiv
                        </td>
                        <td className='px-3 py-2'>
                            Antoan
                        </td>
                        <td className='px-3 py-2'>
                            Beti
                        </td>
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                    </tr>
                    <tr className="border-t text-sm font-semibold text-black">
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                        <td className='px-3 py-2'>
                            Plovdiv
                        </td>
                        <td className='px-3 py-2'>
                            Antoan
                        </td>
                        <td className='px-3 py-2'>
                            Beti
                        </td>
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                    </tr>
                    <tr className="border-t text-sm font-semibold text-black">
                        <th 
                            className="bg-[#FAF9FB] px-3 py-2 text-left" 
                            colSpan={6}
                            scope="colgroup"
                        >
                            Registered
                        </th>
                    </tr>
                    <tr className="border-t text-sm font-semibold text-black">
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                        <td className='px-3 py-2'>
                            Plovdiv
                        </td>
                        <td className='px-3 py-2'>
                            Antoan
                        </td>
                        <td className='px-3 py-2'>
                            Beti
                        </td>
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                    </tr>
                    <tr className="border-t text-sm font-semibold text-black">
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                        <td className='px-3 py-2'>
                            Plovdiv
                        </td>
                        <td className='px-3 py-2'>
                            Antoan
                        </td>
                        <td className='px-3 py-2'>
                            Beti
                        </td>
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                        <td className='px-3 py-2'>
                            Sofia
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    );
};

export default Table;