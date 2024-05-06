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

import { getEntities } from './legesgrondslag.reducer';

export const Legesgrondslag = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const legesgrondslagList = useAppSelector(state => state.legesgrondslag.entities);
  const loading = useAppSelector(state => state.legesgrondslag.loading);

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
      <h2 id="legesgrondslag-heading" data-cy="LegesgrondslagHeading">
        Legesgrondslags
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/legesgrondslag/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Legesgrondslag
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {legesgrondslagList && legesgrondslagList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aangemaaktdoor')}>
                  Aangemaaktdoor <FontAwesomeIcon icon={getSortIconByFieldName('aangemaaktdoor')} />
                </th>
                <th className="hand" onClick={sort('aantalopgegeven')}>
                  Aantalopgegeven <FontAwesomeIcon icon={getSortIconByFieldName('aantalopgegeven')} />
                </th>
                <th className="hand" onClick={sort('aantalvastgesteld')}>
                  Aantalvastgesteld <FontAwesomeIcon icon={getSortIconByFieldName('aantalvastgesteld')} />
                </th>
                <th className="hand" onClick={sort('automatisch')}>
                  Automatisch <FontAwesomeIcon icon={getSortIconByFieldName('automatisch')} />
                </th>
                <th className="hand" onClick={sort('datumaanmaak')}>
                  Datumaanmaak <FontAwesomeIcon icon={getSortIconByFieldName('datumaanmaak')} />
                </th>
                <th className="hand" onClick={sort('datummutatie')}>
                  Datummutatie <FontAwesomeIcon icon={getSortIconByFieldName('datummutatie')} />
                </th>
                <th className="hand" onClick={sort('eenheid')}>
                  Eenheid <FontAwesomeIcon icon={getSortIconByFieldName('eenheid')} />
                </th>
                <th className="hand" onClick={sort('gemuteerddoor')}>
                  Gemuteerddoor <FontAwesomeIcon icon={getSortIconByFieldName('gemuteerddoor')} />
                </th>
                <th className="hand" onClick={sort('legesgrondslag')}>
                  Legesgrondslag <FontAwesomeIcon icon={getSortIconByFieldName('legesgrondslag')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {legesgrondslagList.map((legesgrondslag, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/legesgrondslag/${legesgrondslag.id}`} color="link" size="sm">
                      {legesgrondslag.id}
                    </Button>
                  </td>
                  <td>{legesgrondslag.aangemaaktdoor}</td>
                  <td>{legesgrondslag.aantalopgegeven}</td>
                  <td>{legesgrondslag.aantalvastgesteld}</td>
                  <td>{legesgrondslag.automatisch}</td>
                  <td>{legesgrondslag.datumaanmaak}</td>
                  <td>
                    {legesgrondslag.datummutatie ? (
                      <TextFormat type="date" value={legesgrondslag.datummutatie} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{legesgrondslag.eenheid}</td>
                  <td>{legesgrondslag.gemuteerddoor}</td>
                  <td>{legesgrondslag.legesgrondslag}</td>
                  <td>{legesgrondslag.omschrijving}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/legesgrondslag/${legesgrondslag.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/legesgrondslag/${legesgrondslag.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/legesgrondslag/${legesgrondslag.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Legesgrondslags found</div>
        )}
      </div>
    </div>
  );
};

export default Legesgrondslag;
