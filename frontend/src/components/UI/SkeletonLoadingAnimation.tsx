import React, { Fragment, useMemo } from "react";

type SkeletonLoadingAnimationProps = {
    header?: "full" | "small" | "tabs" | false;
    layout?: "table" | "column" | false;
    layoutWidth?: string;
    layoutItemHeight?: string;
    layoutItems?: number;
};

const SkeletonLoadingAnimation: React.FC<SkeletonLoadingAnimationProps> = ({
   header = "full",
   layout = "table",
   layoutWidth = "100%",
   layoutItemHeight = "200px",
   layoutItems = 1
}) => {

    /*
        Overview: useMemo lets you cache the result of a calculation between re-renders.

        Reference: useMemo(() => any, [dependencies])

        - On initial render, React will call this function.
        - On next renders, React will return the same value if the dependencies have not changed. Otherwise, it will call the function again.

    */
    const layoutWidthStyle = useMemo(
        () => ({
            width: layoutWidth,
        }),
        [layoutWidth]
    );

    const layoutItemHeightStyle = useMemo(
        () => ({
            height: layoutItemHeight,
        }),
        [layoutItemHeight]
    );

    const layoutItemsArray: number[] = useMemo(
        () => Array.from(Array(layoutItems).keys()),
        [layoutItems]
    );

    const shimmerStyles = useMemo(
        () => ({
            backgroundImage:
                "linear-gradient(to right, rgba(211, 211, 211, 0) 0%, rgba(211, 211, 211, 0.2) 20%, rgba(211, 211, 211, 0.5) 50%, rgba(211, 211, 211, 0))",
            backgroundSize: "200% 100%",
            border: "1px solid rgba(0, 0, 0, 0.05)",
            borderRadius: "5px",
        }),
        []
    );

    return (
        <section className="flex flex-wrap justify-center items-center w-full gap-2.5 px-2 py-2.5">
            {header && (
                <div
                    className={`
                        min-w-full h-[30px] my-2.5
                        ${
                        header === "tabs"
                            ? "grid justify-center gap-2.5 grid-cols-[25%,_25%,_25%]"
                            : "w-full"
                    }
                    `.trim()}
                >
                    {header === "tabs" ? (
                        <Fragment>
                            <div className='animate-shimmer' style={shimmerStyles} />
                            <div className='animate-shimmer' style={shimmerStyles} />
                            <div className='animate-shimmer' style={shimmerStyles} />
                        </Fragment>
                    ) : (
                        <div
                            className={`
                                h-[30px] animate-shimmer
                                ${header === "small" ? "w-[30%]" : "w-full"}
                            `}
                            style={shimmerStyles}
                        />
                    )}
                </div>
            )}
            {layout &&
                <div
                    className={`
                        max-w-full
                        grid gap-2.5
                        ${layout === "column" ? "grid-flow-col" : "grid-flow-row"}
                    `.trim()}
                    style={layoutWidthStyle}
                >
                    { layoutItemsArray.map((item: number) => (
                        <div
                            key={item}
                            className='animate-shimmer'
                            style={{...layoutItemHeightStyle, ...shimmerStyles}}
                        />
                    ))}
                </div>
            }
        </section>
    );
};

export default SkeletonLoadingAnimation;
