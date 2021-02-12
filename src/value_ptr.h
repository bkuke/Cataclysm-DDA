#pragma once
#ifndef CATA_SRC_VALUE_PTR_H
#define CATA_SRC_VALUE_PTR_H

#include <memory>

class JsonIn;
class JsonOut;

namespace cata
{

/**
 * This class is essentially a copyable unique pointer. Its purpose is to allow
 * for sparse storage of data without wasting memory in classes such as itype.
 */
//就是一个可以复制的unique_ptr，另外多了两个函数，分别用于序列化和反序列化json。
template <class T>
class value_ptr : public std::unique_ptr<T>
{
    public:
        value_ptr() = default;
        value_ptr( value_ptr && ) = default;
        // NOLINTNEXTLINE(google-explicit-constructor)
        value_ptr( std::nullptr_t ) {}
        explicit value_ptr( T *value ) : std::unique_ptr<T>( value ) {}
        value_ptr( const value_ptr<T> &other ) :
            std::unique_ptr<T>( other ? new T( *other ) : nullptr ) {}
        value_ptr &operator=( value_ptr<T> other ) {
            // 等价于*this=move(other);然后返回 unique_ptr类的*this对象引用，但是返回值没有使用。
            std::unique_ptr<T>::operator=( std::move( other ) );
            //返回value_ptr类的this引用
            return *this;
        }

        template<typename Stream = JsonOut>
        void serialize( Stream &jsout ) const {
            if( this->get() ) {
                this->get()->serialize( jsout );
            } else {
                jsout.write_null();
            }
        }
        template<typename Stream = JsonIn>
        void deserialize( Stream &jsin ) {
            if( jsin.test_null() ) {
                this->reset();
            } else {
                this->reset( new T() );
                this->get()->deserialize( jsin );
            }
        }
};

template <class T, class... Args>
value_ptr<T> make_value( Args &&...args )
{
    return value_ptr<T>( new T( std::forward<Args>( args )... ) );
}

template <class T>
bool value_ptr_equals( const value_ptr<T> &lhs, const value_ptr<T> &rhs )
{
    return ( !lhs && !rhs ) || ( lhs && rhs && *lhs == *rhs );
}

} // namespace cata

#endif // CATA_SRC_VALUE_PTR_H
