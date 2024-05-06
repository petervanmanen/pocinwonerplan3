import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './besluit.reducer';

export const Besluit = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const besluitList = useAppSelector(state => state.besluit.entities);
  const loading = useAppSelector(state => state.besluit.loading);

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
      <h2 id="besluit-heading" data-cy="BesluitHeading">
        Besluits
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/besluit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Besluit
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {besluitList && besluitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('besluit')}>
                  Besluit <FontAwesomeIcon icon={getSortIconByFieldName('besluit')} />
                </th>
                <th className="hand" onClick={sort('besluitidentificatie')}>
                  Besluitidentificatie <FontAwesomeIcon icon={getSortIconByFieldName('besluitidentificatie')} />
                </th>
                <th className="hand" onClick={sort('besluittoelichting')}>
                  Besluittoelichting <FontAwesomeIcon icon={getSortIconByFieldName('besluittoelichting')} />
                </th>
                <th className="hand" onClick={sort('datumbesluit')}>
                  Datumbesluit <FontAwesomeIcon icon={getSortIconByFieldName('datumbesluit')} />
                </th>
                <th className="hand" onClick={sort('datumpublicatie')}>
                  Datumpublicatie <FontAwesomeIcon icon={getSortIconByFieldName('datumpublicatie')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('datumuiterlijkereactie')}>
                  Datumuiterlijkereactie <FontAwesomeIcon icon={getSortIconByFieldName('datumuiterlijkereactie')} />
                </th>
                <th className="hand" onClick={sort('datumverval')}>
                  Datumverval <FontAwesomeIcon icon={getSortIconByFieldName('datumverval')} />
                </th>
                <th className="hand" onClick={sort('datumverzending')}>
                  Datumverzending <FontAwesomeIcon icon={getSortIconByFieldName('datumverzending')} />
                </th>
                <th className="hand" onClick={sort('redenverval')}>
                  Redenverval <FontAwesomeIcon icon={getSortIconByFieldName('redenverval')} />
                </th>
                <th>
                  Isvastgelegdin Document <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isuitkomstvan Zaak <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isvan Besluittype <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Kanvastgelegdzijnals Document <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {besluitList.map((besluit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/besluit/${besluit.id}`} color="link" size="sm">
                      {besluit.id}
                    </Button>
                  </td>
                  <td>{besluit.besluit}</td>
                  <td>{besluit.besluitidentificatie}</td>
                  <td>{besluit.besluittoelichting}</td>
                  <td>{besluit.datumbesluit}</td>
                  <td>{besluit.datumpublicatie}</td>
                  <td>{besluit.datumstart}</td>
                  <td>{besluit.datumuiterlijkereactie}</td>
                  <td>{besluit.datumverval}</td>
                  <td>{besluit.datumverzending}</td>
                  <td>{besluit.redenverval}</td>
                  <td>
                    {besluit.isvastgelegdinDocument ? (
                      <Link to={`/document/${besluit.isvastgelegdinDocument.id}`}>{besluit.isvastgelegdinDocument.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {besluit.isuitkomstvanZaak ? (
                      <Link to={`/zaak/${besluit.isuitkomstvanZaak.id}`}>{besluit.isuitkomstvanZaak.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {besluit.isvanBesluittype ? (
                      <Link to={`/besluittype/${besluit.isvanBesluittype.id}`}>{besluit.isvanBesluittype.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {besluit.kanvastgelegdzijnalsDocuments
                      ? besluit.kanvastgelegdzijnalsDocuments.map((val, j) => (
                          <span key={j}>
                            <Link to={`/document/${val.id}`}>{val.id}</Link>
                            {j === besluit.kanvastgelegdzijnalsDocuments.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/besluit/${besluit.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/besluit/${besluit.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/besluit/${besluit.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Besluits found</div>
        )}
      </div>
    </div>
  );
};

export default Besluit;
