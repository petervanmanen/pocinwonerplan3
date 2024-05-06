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

import { getEntities } from './klantbeoordeling.reducer';

export const Klantbeoordeling = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const klantbeoordelingList = useAppSelector(state => state.klantbeoordeling.entities);
  const loading = useAppSelector(state => state.klantbeoordeling.loading);

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
      <h2 id="klantbeoordeling-heading" data-cy="KlantbeoordelingHeading">
        Klantbeoordelings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/klantbeoordeling/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Klantbeoordeling
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {klantbeoordelingList && klantbeoordelingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('beoordeling')}>
                  Beoordeling <FontAwesomeIcon icon={getSortIconByFieldName('beoordeling')} />
                </th>
                <th className="hand" onClick={sort('categorie')}>
                  Categorie <FontAwesomeIcon icon={getSortIconByFieldName('categorie')} />
                </th>
                <th className="hand" onClick={sort('contactopnemen')}>
                  Contactopnemen <FontAwesomeIcon icon={getSortIconByFieldName('contactopnemen')} />
                </th>
                <th className="hand" onClick={sort('ddbeoordeling')}>
                  Ddbeoordeling <FontAwesomeIcon icon={getSortIconByFieldName('ddbeoordeling')} />
                </th>
                <th className="hand" onClick={sort('kanaal')}>
                  Kanaal <FontAwesomeIcon icon={getSortIconByFieldName('kanaal')} />
                </th>
                <th className="hand" onClick={sort('onderwerp')}>
                  Onderwerp <FontAwesomeIcon icon={getSortIconByFieldName('onderwerp')} />
                </th>
                <th className="hand" onClick={sort('subcategorie')}>
                  Subcategorie <FontAwesomeIcon icon={getSortIconByFieldName('subcategorie')} />
                </th>
                <th>
                  Doet Betrokkene <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {klantbeoordelingList.map((klantbeoordeling, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/klantbeoordeling/${klantbeoordeling.id}`} color="link" size="sm">
                      {klantbeoordeling.id}
                    </Button>
                  </td>
                  <td>{klantbeoordeling.beoordeling}</td>
                  <td>{klantbeoordeling.categorie}</td>
                  <td>{klantbeoordeling.contactopnemen ? 'true' : 'false'}</td>
                  <td>
                    {klantbeoordeling.ddbeoordeling ? (
                      <TextFormat type="date" value={klantbeoordeling.ddbeoordeling} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{klantbeoordeling.kanaal}</td>
                  <td>{klantbeoordeling.onderwerp}</td>
                  <td>{klantbeoordeling.subcategorie}</td>
                  <td>
                    {klantbeoordeling.doetBetrokkene ? (
                      <Link to={`/betrokkene/${klantbeoordeling.doetBetrokkene.id}`}>{klantbeoordeling.doetBetrokkene.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/klantbeoordeling/${klantbeoordeling.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/klantbeoordeling/${klantbeoordeling.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/klantbeoordeling/${klantbeoordeling.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Klantbeoordelings found</div>
        )}
      </div>
    </div>
  );
};

export default Klantbeoordeling;
