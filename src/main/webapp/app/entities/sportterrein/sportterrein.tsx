import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './sportterrein.reducer';

export const Sportterrein = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const sportterreinList = useAppSelector(state => state.sportterrein.entities);
  const loading = useAppSelector(state => state.sportterrein.loading);

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
      <h2 id="sportterrein-heading" data-cy="SportterreinHeading">
        Sportterreins
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/sportterrein/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Sportterrein
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sportterreinList && sportterreinList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('drainage')}>
                  Drainage <FontAwesomeIcon icon={getSortIconByFieldName('drainage')} />
                </th>
                <th className="hand" onClick={sort('gebruiksvorm')}>
                  Gebruiksvorm <FontAwesomeIcon icon={getSortIconByFieldName('gebruiksvorm')} />
                </th>
                <th className="hand" onClick={sort('sportcomplex')}>
                  Sportcomplex <FontAwesomeIcon icon={getSortIconByFieldName('sportcomplex')} />
                </th>
                <th className="hand" onClick={sort('sportterreintypesport')}>
                  Sportterreintypesport <FontAwesomeIcon icon={getSortIconByFieldName('sportterreintypesport')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('typeplus')}>
                  Typeplus <FontAwesomeIcon icon={getSortIconByFieldName('typeplus')} />
                </th>
                <th className="hand" onClick={sort('veldnummer')}>
                  Veldnummer <FontAwesomeIcon icon={getSortIconByFieldName('veldnummer')} />
                </th>
                <th className="hand" onClick={sort('verlicht')}>
                  Verlicht <FontAwesomeIcon icon={getSortIconByFieldName('verlicht')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sportterreinList.map((sportterrein, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sportterrein/${sportterrein.id}`} color="link" size="sm">
                      {sportterrein.id}
                    </Button>
                  </td>
                  <td>{sportterrein.drainage ? 'true' : 'false'}</td>
                  <td>{sportterrein.gebruiksvorm}</td>
                  <td>{sportterrein.sportcomplex}</td>
                  <td>{sportterrein.sportterreintypesport}</td>
                  <td>{sportterrein.type}</td>
                  <td>{sportterrein.typeplus}</td>
                  <td>{sportterrein.veldnummer}</td>
                  <td>{sportterrein.verlicht ? 'true' : 'false'}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/sportterrein/${sportterrein.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/sportterrein/${sportterrein.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/sportterrein/${sportterrein.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Sportterreins found</div>
        )}
      </div>
    </div>
  );
};

export default Sportterrein;
