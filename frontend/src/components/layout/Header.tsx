interface HeaderProps {
  title?: string
}

export function Header({ title = 'School Management' }: HeaderProps) {
  return (
    <header className="header">
      <h1 className="header__title">{title}</h1>
    </header>
  )
}
