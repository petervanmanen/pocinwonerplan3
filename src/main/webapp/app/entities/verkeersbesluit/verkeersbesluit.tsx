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

import { getEntities } from './verkeersbesluit.reducer';

export const Verkeersbesluit = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const verkeersbesluitList = useAppSelector(state => state.verkeersbesluit.entities);
  const loading = useAppSelector(state => state.verkeersbesluit.loading);

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
      <h2 id="verkeersbesluit-heading" data-cy="VerkeersbesluitHeading">
        Verkeersbesluits
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/verkeersbesluit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Verkeersbesluit
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {verkeersbesluitList && verkeersbesluitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbesluit')}>
                  Datumbesluit <FontAwesomeIcon icon={getSortIconByFieldName('datumbesluit')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('huisnummer')}>
                  Huisnummer <FontAwesomeIcon icon={getSortIconByFieldName('huisnummer')} />
                </th>
                <th className="hand" onClick={sort('postcode')}>
                  Postcode <FontAwesomeIcon icon={getSortIconByFieldName('postcode')} />
                </th>
                <th className="hand" onClick={sort('referentienummer')}>
                  Referentienummer <FontAwesomeIcon icon={getSortIconByFieldName('referentienummer')} />
                </th>
                <th className="hand" onClick={sort('straat')}>
                  Straat <FontAwesomeIcon icon={getSortIconByFieldName('straat')} />
                </th>
                <th className="hand" onClick={sort('titel')}>
                  Titel <FontAwesomeIcon icon={getSortIconByFieldName('titel')} />
                </th>
                <th>
                  Isvastgelegdin Document <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {verkeersbesluitList.map((verkeersbesluit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/verkeersbesluit/${verkeersbesluit.id}`} color="link" size="sm">
                      {verkeersbesluit.id}
                    </Button>
                  </td>
                  <td>
                    {verkeersbesluit.datumbesluit ? (
                      <TextFormat type="date" value={verkeersbesluit.datumbesluit} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{verkeersbesluit.datumeinde}</td>
                  <td>{verkeersbesluit.datumstart}</td>
                  <td>{verkeersbesluit.huisnummer}</td>
                  <td>{verkeersbesluit.postcode}</td>
                  <td>{verkeersbesluit.referentienummer}</td>
                  <td>{verkeersbesluit.straat}</td>
                  <td>{verkeersbesluit.titel}</td>
                  <td>
                    {verkeersbesluit.isvastgelegdinDocument ? (
                      <Link to={`/document/${verkeersbesluit.isvastgelegdinDocument.id}`}>{verkeersbesluit.isvastgelegdinDocument.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/verkeersbesluit/${verkeersbesluit.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/verkeersbesluit/${verkeersbesluit.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/verkeersbesluit/${verkeersbesluit.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Verkeersbesluits found</div>
        )}
      </div>
    </div>
  );
};

export default Verkeersbesluit;
