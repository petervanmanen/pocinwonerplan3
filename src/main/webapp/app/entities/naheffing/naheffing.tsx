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

import { getEntities } from './naheffing.reducer';

export const Naheffing = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const naheffingList = useAppSelector(state => state.naheffing.entities);
  const loading = useAppSelector(state => state.naheffing.loading);

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
      <h2 id="naheffing-heading" data-cy="NaheffingHeading">
        Naheffings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/naheffing/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Naheffing
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {naheffingList && naheffingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('bedrag')}>
                  Bedrag <FontAwesomeIcon icon={getSortIconByFieldName('bedrag')} />
                </th>
                <th className="hand" onClick={sort('bezwaarafgehandeld')}>
                  Bezwaarafgehandeld <FontAwesomeIcon icon={getSortIconByFieldName('bezwaarafgehandeld')} />
                </th>
                <th className="hand" onClick={sort('bezwaaringetrokken')}>
                  Bezwaaringetrokken <FontAwesomeIcon icon={getSortIconByFieldName('bezwaaringetrokken')} />
                </th>
                <th className="hand" onClick={sort('bezwaartoegewezen')}>
                  Bezwaartoegewezen <FontAwesomeIcon icon={getSortIconByFieldName('bezwaartoegewezen')} />
                </th>
                <th className="hand" onClick={sort('bonnummer')}>
                  Bonnummer <FontAwesomeIcon icon={getSortIconByFieldName('bonnummer')} />
                </th>
                <th className="hand" onClick={sort('datumbetaling')}>
                  Datumbetaling <FontAwesomeIcon icon={getSortIconByFieldName('datumbetaling')} />
                </th>
                <th className="hand" onClick={sort('datumbezwaar')}>
                  Datumbezwaar <FontAwesomeIcon icon={getSortIconByFieldName('datumbezwaar')} />
                </th>
                <th className="hand" onClick={sort('datumgeseponeerd')}>
                  Datumgeseponeerd <FontAwesomeIcon icon={getSortIconByFieldName('datumgeseponeerd')} />
                </th>
                <th className="hand" onClick={sort('datumindiening')}>
                  Datumindiening <FontAwesomeIcon icon={getSortIconByFieldName('datumindiening')} />
                </th>
                <th className="hand" onClick={sort('dienstcd')}>
                  Dienstcd <FontAwesomeIcon icon={getSortIconByFieldName('dienstcd')} />
                </th>
                <th className="hand" onClick={sort('fiscaal')}>
                  Fiscaal <FontAwesomeIcon icon={getSortIconByFieldName('fiscaal')} />
                </th>
                <th className="hand" onClick={sort('organisatie')}>
                  Organisatie <FontAwesomeIcon icon={getSortIconByFieldName('organisatie')} />
                </th>
                <th className="hand" onClick={sort('overtreding')}>
                  Overtreding <FontAwesomeIcon icon={getSortIconByFieldName('overtreding')} />
                </th>
                <th className="hand" onClick={sort('parkeertarief')}>
                  Parkeertarief <FontAwesomeIcon icon={getSortIconByFieldName('parkeertarief')} />
                </th>
                <th className="hand" onClick={sort('redenseponeren')}>
                  Redenseponeren <FontAwesomeIcon icon={getSortIconByFieldName('redenseponeren')} />
                </th>
                <th className="hand" onClick={sort('vorderingnummer')}>
                  Vorderingnummer <FontAwesomeIcon icon={getSortIconByFieldName('vorderingnummer')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {naheffingList.map((naheffing, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/naheffing/${naheffing.id}`} color="link" size="sm">
                      {naheffing.id}
                    </Button>
                  </td>
                  <td>{naheffing.bedrag}</td>
                  <td>
                    {naheffing.bezwaarafgehandeld ? (
                      <TextFormat type="date" value={naheffing.bezwaarafgehandeld} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {naheffing.bezwaaringetrokken ? (
                      <TextFormat type="date" value={naheffing.bezwaaringetrokken} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {naheffing.bezwaartoegewezen ? (
                      <TextFormat type="date" value={naheffing.bezwaartoegewezen} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{naheffing.bonnummer}</td>
                  <td>
                    {naheffing.datumbetaling ? (
                      <TextFormat type="date" value={naheffing.datumbetaling} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {naheffing.datumbezwaar ? (
                      <TextFormat type="date" value={naheffing.datumbezwaar} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {naheffing.datumgeseponeerd ? (
                      <TextFormat type="date" value={naheffing.datumgeseponeerd} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {naheffing.datumindiening ? (
                      <TextFormat type="date" value={naheffing.datumindiening} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{naheffing.dienstcd}</td>
                  <td>{naheffing.fiscaal ? 'true' : 'false'}</td>
                  <td>{naheffing.organisatie}</td>
                  <td>{naheffing.overtreding}</td>
                  <td>{naheffing.parkeertarief}</td>
                  <td>{naheffing.redenseponeren}</td>
                  <td>{naheffing.vorderingnummer}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/naheffing/${naheffing.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/naheffing/${naheffing.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/naheffing/${naheffing.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Naheffings found</div>
        )}
      </div>
    </div>
  );
};

export default Naheffing;
