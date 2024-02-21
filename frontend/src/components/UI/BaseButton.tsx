import React, {MouseEventHandler} from "react";

type buttonProps = {
    onClick?: MouseEventHandler<HTMLButtonElement>,
    className?: string,
    children: React.ReactNode | string,
    fill?: boolean,
    small?: boolean,
    secondary?: boolean,
    disabled?: boolean,
    type?: "button" | "submit",
    form?: string,
}

const Button: React.FC<buttonProps> = ({
    onClick,
    className = "",
    children,
    fill,
    small,
    secondary,
    disabled,
    type,
    form
}) => {

    return (
        <button
            type={type}
            disabled={disabled}
            onClick={onClick}
            form={form}
            className={`
                ${className}
                inline-flex justify-center items-center w-fit h-fit whitespace-nowrap text-[15px] rounded-[32px] font-bold
                ${fill ?
                    "text-amber-50 bg-green-500 hover:bg-green-600" :
                    "text-main bg-white border border-solid hover:bg-gray-100"
                }
                ${secondary ? 
                    `${fill ? 
                        "!border-0 !text-[#ced1d3] !bg-[#54595f] hover:!bg-[#424548]" :
                        "!border-0 !text-[#54595f] !bg-[#abb0b5] hover:!bg-[#ced1d3]"
                    }` :
                    ""
                }
                ${small ? "px-[20px] py-[10px]" : "px-[26px] py-[13px]"}
                focus:shadow-lg
                disabled:opacity-[.65] disabled:cursor-not-allowed disabled:bg-gray 
            `}
        >
            {children}
        </button>
    );
};

export default Button;