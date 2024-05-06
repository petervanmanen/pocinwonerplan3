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

import { getEntities } from './beoordeling.reducer';

export const Beoordeling = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const beoordelingList = useAppSelector(state => state.beoordeling.entities);
  const loading = useAppSelector(state => state.beoordeling.loading);

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
      <h2 id="beoordeling-heading" data-cy="BeoordelingHeading">
        Beoordelings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/beoordeling/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Beoordeling
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {beoordelingList && beoordelingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datum')}>
                  Datum <FontAwesomeIcon icon={getSortIconByFieldName('datum')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('oordeel')}>
                  Oordeel <FontAwesomeIcon icon={getSortIconByFieldName('oordeel')} />
                </th>
                <th>
                  Beoordeeltdoor Werknemer <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Beoordelingvan Werknemer <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {beoordelingList.map((beoordeling, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/beoordeling/${beoordeling.id}`} color="link" size="sm">
                      {beoordeling.id}
                    </Button>
                  </td>
                  <td>{beoordeling.datum ? <TextFormat type="date" value={beoordeling.datum} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{beoordeling.omschrijving}</td>
                  <td>{beoordeling.oordeel}</td>
                  <td>
                    {beoordeling.beoordeeltdoorWerknemer ? (
                      <Link to={`/werknemer/${beoordeling.beoordeeltdoorWerknemer.id}`}>{beoordeling.beoordeeltdoorWerknemer.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {beoordeling.beoordelingvanWerknemer ? (
                      <Link to={`/werknemer/${beoordeling.beoordelingvanWerknemer.id}`}>{beoordeling.beoordelingvanWerknemer.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/beoordeling/${beoordeling.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/beoordeling/${beoordeling.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/beoordeling/${beoordeling.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Beoordelings found</div>
        )}
      </div>
    </div>
  );
};

export default Beoordeling;
