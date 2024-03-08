'use client';

import React, { useState } from "react";
import {useRouter} from "next/navigation";

//UI components
import BaseArrow from "@/components/UI/BaseArrow";

export type selectorItem = {
    title: string,
    code: string,
    href?: string;
	id?: number | string;
}

type DataSelectorWrapperProps = {
	hasInitialPlaceholderValue?: boolean;
	placeholderValue: string;
	selectorData: selectorItem[];
	closeOnLeave?: boolean;
	onResubForNewData: (data: selectorItem) => void;
};

const DataSelectorWrapper: React.FC<DataSelectorWrapperProps> = ({
	hasInitialPlaceholderValue,
	placeholderValue,
	selectorData,
	closeOnLeave = false,
	onResubForNewData
}) => {
	const router = useRouter();

	//when we have hasInitialPlaceholderValue, we should hide the list (false)
	//when we don't have hasInitialPlaceholderValue, we should show the list (true)
	const [isSelectorClicked, setIsSelectorClicked] = useState<boolean>(!hasInitialPlaceholderValue);

	const expandCollapseSelector = () => {
		setIsSelectorClicked((prevState) => !prevState);
	};

	const switchData = (title: string, code: string, href?: string, id?: string | number) => {
		setIsSelectorClicked(false);

		onResubForNewData({
			title,
			code,
			href,
			id
		});

		if (href) {
			//programmatically changes route
			router.push(href);
		}
	};

	return (
		<div
			onMouseLeave={
				closeOnLeave ? () => setIsSelectorClicked(false) : undefined
			}
			onKeyDown={closeOnLeave ? () => setIsSelectorClicked(false) : undefined}
			className={`
                ${hasInitialPlaceholderValue ? 
					"relative min-w-[200px] h-[45px]" :
					"absolute top-[30px] right-0 z-10"
				}
                text-black text-base font-bold capitalize cursor-pointer
            `.trim()}
		>
			{hasInitialPlaceholderValue && (
				<div
					onClick={expandCollapseSelector}
					onKeyUp={expandCollapseSelector}
					className={`
						flex justify-between items-center w-full h-full p-1
						border border-solid border-[#E5E5E5] rounded-md
						${isSelectorClicked? "bg-[#F1F1F1]" : "bg-white"}
					`}
				>
					<span>{placeholderValue}</span>
					<BaseArrow rotate={isSelectorClicked} />
				</div>
			)}

			{isSelectorClicked && (
				<ul 
					className={`
						${hasInitialPlaceholderValue ? "absolute top-[46px] left-0 z-10" : ""}
						flex flex-col justify-start items-start
						${hasInitialPlaceholderValue ? 'min-w-[200px]' : 'w-max'} py-1
						border border-solid border-[#E5E5E5] rounded-md
						shadow-md bg-[rgba(255,_255,_255,_0.9)] backdrop-blur-sm
					`}
					role="list"
				>
					{selectorData.map((item: selectorItem) => {
						return (
							<li
								key={item.code}
								onClick={switchData.bind(
									null,
									item.title,
									item.code,
									item.href ?? undefined,
									item.id ?? undefined
								)}
								className={`
									w-full leading-[45px] pl-[15px] pr-[10px] cursor-pointer
									${ placeholderValue.toLocaleLowerCase() === item.title.toLocaleLowerCase() ?
										"bg-[#F1F1F1]" :
										"bg-white"
									}
									hover:bg-[#F1F1F1]
								`}
							>
								{item.title}
							</li>
						);
					})}
				</ul>
			)}
		</div>
	);
};

export default DataSelectorWrapper;