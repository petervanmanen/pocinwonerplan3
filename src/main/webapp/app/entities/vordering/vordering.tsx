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

import { getEntities } from './vordering.reducer';

export const Vordering = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vorderingList = useAppSelector(state => state.vordering.entities);
  const loading = useAppSelector(state => state.vordering.loading);

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
      <h2 id="vordering-heading" data-cy="VorderingHeading">
        Vorderings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vordering/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vordering
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vorderingList && vorderingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aangemaaktdoor')}>
                  Aangemaaktdoor <FontAwesomeIcon icon={getSortIconByFieldName('aangemaaktdoor')} />
                </th>
                <th className="hand" onClick={sort('bedragbtw')}>
                  Bedragbtw <FontAwesomeIcon icon={getSortIconByFieldName('bedragbtw')} />
                </th>
                <th className="hand" onClick={sort('datumaanmaak')}>
                  Datumaanmaak <FontAwesomeIcon icon={getSortIconByFieldName('datumaanmaak')} />
                </th>
                <th className="hand" onClick={sort('datummutatie')}>
                  Datummutatie <FontAwesomeIcon icon={getSortIconByFieldName('datummutatie')} />
                </th>
                <th className="hand" onClick={sort('geaccordeerd')}>
                  Geaccordeerd <FontAwesomeIcon icon={getSortIconByFieldName('geaccordeerd')} />
                </th>
                <th className="hand" onClick={sort('geaccordeerddoor')}>
                  Geaccordeerddoor <FontAwesomeIcon icon={getSortIconByFieldName('geaccordeerddoor')} />
                </th>
                <th className="hand" onClick={sort('geaccordeerdop')}>
                  Geaccordeerdop <FontAwesomeIcon icon={getSortIconByFieldName('geaccordeerdop')} />
                </th>
                <th className="hand" onClick={sort('geexporteerd')}>
                  Geexporteerd <FontAwesomeIcon icon={getSortIconByFieldName('geexporteerd')} />
                </th>
                <th className="hand" onClick={sort('gemuteerddoor')}>
                  Gemuteerddoor <FontAwesomeIcon icon={getSortIconByFieldName('gemuteerddoor')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('totaalbedrag')}>
                  Totaalbedrag <FontAwesomeIcon icon={getSortIconByFieldName('totaalbedrag')} />
                </th>
                <th className="hand" onClick={sort('totaalbedraginclusief')}>
                  Totaalbedraginclusief <FontAwesomeIcon icon={getSortIconByFieldName('totaalbedraginclusief')} />
                </th>
                <th className="hand" onClick={sort('vorderingnummer')}>
                  Vorderingnummer <FontAwesomeIcon icon={getSortIconByFieldName('vorderingnummer')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vorderingList.map((vordering, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vordering/${vordering.id}`} color="link" size="sm">
                      {vordering.id}
                    </Button>
                  </td>
                  <td>{vordering.aangemaaktdoor}</td>
                  <td>{vordering.bedragbtw}</td>
                  <td>
                    {vordering.datumaanmaak ? (
                      <TextFormat type="date" value={vordering.datumaanmaak} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {vordering.datummutatie ? (
                      <TextFormat type="date" value={vordering.datummutatie} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{vordering.geaccordeerd}</td>
                  <td>{vordering.geaccordeerddoor}</td>
                  <td>
                    {vordering.geaccordeerdop ? (
                      <TextFormat type="date" value={vordering.geaccordeerdop} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{vordering.geexporteerd}</td>
                  <td>{vordering.gemuteerddoor}</td>
                  <td>{vordering.omschrijving}</td>
                  <td>{vordering.totaalbedrag}</td>
                  <td>{vordering.totaalbedraginclusief}</td>
                  <td>{vordering.vorderingnummer}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vordering/${vordering.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/vordering/${vordering.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vordering/${vordering.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vorderings found</div>
        )}
      </div>
    </div>
  );
};

export default Vordering;
