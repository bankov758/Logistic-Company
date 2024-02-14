import Icon from "@/components/UI/Icon";
import React from "react";

type inputProps = {
	id: string;
	type: string;
	required?: boolean;
	name: string;
	placeholder?: string;
	showRemoveIcon?: boolean;
	inputDisabled?: boolean;
	iconSrc?: string;
	iconAlt?: string;
	iconWidth?: number;
	iconHeight?: number;
};

const Input: React.FC<inputProps> = ({
	id,
	type,
	name,
	required,
	placeholder,
	inputDisabled,
	iconAlt,
	iconWidth,
	iconHeight,
	iconSrc,
	//showRemoveIcon,
}) => {
	return (
		<div className="w-full relative inline-flex justify-start items-center gap-x-2.5 mb-4">
			{iconSrc && (
				<div className="w-6 h-full absolute left-2 top-0 flex justify-center items-center text-black">
					<Icon
						src={iconSrc}
						alt={iconAlt || ""}
						width={iconWidth || 0}
						height={iconHeight || 0}
					/>
				</div>
			)}
			<input
				className="
                    w-full block py-3 pl-10 pr-10 bg-white text-sm text-gray-500 border rounded-3xl border-gray-300 shadow-sm
                    placeholder:text-gray-400
                    focus:border-kingfisher-daisy-950 focus:bg-[#faf6ff] focus:outline-0
                    disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none
                "
				id={id}
				type={type}
				name={name}
				required={required || false}
				placeholder={placeholder}
				disabled={inputDisabled}
			/>
			{/* {showRemoveIcon && enteredValue.toString().length > 0 &&
                <div
                    onClick={() => reset()}
                    className='w-6 h-full absolute top-0 right-4 flex justify-center items-center text-black cursor-pointer pointer-events-auto'
                >
                    <Icon src='/icons/remove.svg' alt='Remove' width={24} height={24}/>
                </div>
            } */}
		</div>
	);
};

export default Input;
