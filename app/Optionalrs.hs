data Optional a = Some a | None deriving (Show, Ord, Eq)

isSome :: Optional a -> Bool
isSome (Some _) = True
isSome None = False
