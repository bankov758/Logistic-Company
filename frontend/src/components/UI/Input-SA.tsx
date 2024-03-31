import Icon from "@/components/UI/Icon";
import React from "react";

type inputProps = {
    id: string;
    type?: string;
    name: string;
    required?: boolean;
    placeholder?: string;
    inputDisabled?: boolean;
    iconSrc?: string;
    iconAlt?: string;
};

const Input: React.FC<inputProps> = ({
    id,
    type = 'text',
    name,
    required = false,
    placeholder,
    inputDisabled,
    iconSrc,
    iconAlt = "Icon",
}) => {
    return (
        <div className="w-full relative inline-flex justify-start items-center gap-x-2.5 mb-4">
            {iconSrc && (
                <div className="w-6 h-full absolute left-2 top-0 flex justify-center items-center text-black">
                    <Icon
                        src={iconSrc}
                        alt={iconAlt}
                    />
                </div>
            )}
            <input
                className={`
                    w-full block py-3 pl-10 pr-10 bg-white text-sm text-gray-500 border rounded-3xl border-gray-300 shadow-sm
                    placeholder:text-gray-400
                    focus:border-kingfisher-daisy-950 focus:bg-[#faf6ff] focus:outline-0
                    disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none
                `.trim()}
                id={id}
                type={type}
                name={name}
                required={required}
                placeholder={placeholder}
                disabled={inputDisabled}
            />
        </div>
    );
};

export default Input;
