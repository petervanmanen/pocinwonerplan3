import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './product.reducer';

export const Product = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const productList = useAppSelector(state => state.product.entities);
  const loading = useAppSelector(state => state.product.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="product-heading" data-cy="ProductHeading">
        Products
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/product/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Product
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {productList && productList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('codemuseumjaarkaart')}>
                  Codemuseumjaarkaart <FontAwesomeIcon icon={getSortIconByFieldName('codemuseumjaarkaart')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheid')}>
                  Datumeindegeldigheid <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheid')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('entreekaart')}>
                  Entreekaart <FontAwesomeIcon icon={getSortIconByFieldName('entreekaart')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('prijs')}>
                  Prijs <FontAwesomeIcon icon={getSortIconByFieldName('prijs')} />
                </th>
                <th>
                  Leverancier Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Kostenplaats <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Valtbinnen Omzetgroep <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Valtbinnen Productgroep <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Doelstelling <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isopdrachtgever Opdrachtgever <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isopdrachtnemer Opdrachtnemer <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productList.map((product, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/product/${product.id}`} color="link" size="sm">
                      {product.id}
                    </Button>
                  </td>
                  <td>{product.codemuseumjaarkaart}</td>
                  <td>
                    {product.datumeindegeldigheid ? (
                      <TextFormat type="date" value={product.datumeindegeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {product.datumstart ? <TextFormat type="date" value={product.datumstart} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{product.entreekaart}</td>
                  <td>{product.omschrijving}</td>
                  <td>{product.prijs}</td>
                  <td>
                    {product.leverancierLeverancier ? (
                      <Link to={`/leverancier/${product.leverancierLeverancier.id}`}>{product.leverancierLeverancier.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {product.heeftKostenplaats ? (
                      <Link to={`/kostenplaats/${product.heeftKostenplaats.id}`}>{product.heeftKostenplaats.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {product.valtbinnenOmzetgroeps
                      ? product.valtbinnenOmzetgroeps.map((val, j) => (
                          <span key={j}>
                            <Link to={`/omzetgroep/${val.id}`}>{val.id}</Link>
                            {j === product.valtbinnenOmzetgroeps.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {product.valtbinnenProductgroeps
                      ? product.valtbinnenProductgroeps.map((val, j) => (
                          <span key={j}>
                            <Link to={`/productgroep/${val.id}`}>{val.id}</Link>
                            {j === product.valtbinnenProductgroeps.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {product.heeftDoelstelling ? (
                      <Link to={`/doelstelling/${product.heeftDoelstelling.id}`}>{product.heeftDoelstelling.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {product.isopdrachtgeverOpdrachtgever ? (
                      <Link to={`/opdrachtgever/${product.isopdrachtgeverOpdrachtgever.id}`}>
                        {product.isopdrachtgeverOpdrachtgever.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {product.isopdrachtnemerOpdrachtnemer ? (
                      <Link to={`/opdrachtnemer/${product.isopdrachtnemerOpdrachtnemer.id}`}>
                        {product.isopdrachtnemerOpdrachtnemer.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/product/${product.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/product/${product.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/product/${product.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Products found</div>
        )}
      </div>
    </div>
  );
};

export default Product;
