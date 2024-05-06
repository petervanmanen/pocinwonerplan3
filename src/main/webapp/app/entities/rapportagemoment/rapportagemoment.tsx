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

import { getEntities } from './rapportagemoment.reducer';

export const Rapportagemoment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const rapportagemomentList = useAppSelector(state => state.rapportagemoment.entities);
  const loading = useAppSelector(state => state.rapportagemoment.loading);

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
      <h2 id="rapportagemoment-heading" data-cy="RapportagemomentHeading">
        Rapportagemoments
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/rapportagemoment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Rapportagemoment
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {rapportagemomentList && rapportagemomentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datum')}>
                  Datum <FontAwesomeIcon icon={getSortIconByFieldName('datum')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('termijn')}>
                  Termijn <FontAwesomeIcon icon={getSortIconByFieldName('termijn')} />
                </th>
                <th>
                  Heeft Subsidie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Projectleider Rechtspersoon <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {rapportagemomentList.map((rapportagemoment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/rapportagemoment/${rapportagemoment.id}`} color="link" size="sm">
                      {rapportagemoment.id}
                    </Button>
                  </td>
                  <td>
                    {rapportagemoment.datum ? (
                      <TextFormat type="date" value={rapportagemoment.datum} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{rapportagemoment.naam}</td>
                  <td>{rapportagemoment.omschrijving}</td>
                  <td>{rapportagemoment.termijn}</td>
                  <td>
                    {rapportagemoment.heeftSubsidie ? (
                      <Link to={`/subsidie/${rapportagemoment.heeftSubsidie.id}`}>{rapportagemoment.heeftSubsidie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {rapportagemoment.projectleiderRechtspersoon ? (
                      <Link to={`/rechtspersoon/${rapportagemoment.projectleiderRechtspersoon.id}`}>
                        {rapportagemoment.projectleiderRechtspersoon.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/rapportagemoment/${rapportagemoment.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/rapportagemoment/${rapportagemoment.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/rapportagemoment/${rapportagemoment.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Rapportagemoments found</div>
        )}
      </div>
    </div>
  );
};

export default Rapportagemoment;
