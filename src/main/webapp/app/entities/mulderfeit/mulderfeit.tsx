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

import { getEntities } from './mulderfeit.reducer';

export const Mulderfeit = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const mulderfeitList = useAppSelector(state => state.mulderfeit.entities);
  const loading = useAppSelector(state => state.mulderfeit.loading);

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
      <h2 id="mulderfeit-heading" data-cy="MulderfeitHeading">
        Mulderfeits
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/mulderfeit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Mulderfeit
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {mulderfeitList && mulderfeitList.length > 0 ? (
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
              {mulderfeitList.map((mulderfeit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/mulderfeit/${mulderfeit.id}`} color="link" size="sm">
                      {mulderfeit.id}
                    </Button>
                  </td>
                  <td>{mulderfeit.bedrag}</td>
                  <td>
                    {mulderfeit.bezwaarafgehandeld ? (
                      <TextFormat type="date" value={mulderfeit.bezwaarafgehandeld} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {mulderfeit.bezwaaringetrokken ? (
                      <TextFormat type="date" value={mulderfeit.bezwaaringetrokken} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {mulderfeit.bezwaartoegewezen ? (
                      <TextFormat type="date" value={mulderfeit.bezwaartoegewezen} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{mulderfeit.bonnummer}</td>
                  <td>
                    {mulderfeit.datumbetaling ? (
                      <TextFormat type="date" value={mulderfeit.datumbetaling} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {mulderfeit.datumbezwaar ? (
                      <TextFormat type="date" value={mulderfeit.datumbezwaar} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {mulderfeit.datumgeseponeerd ? (
                      <TextFormat type="date" value={mulderfeit.datumgeseponeerd} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {mulderfeit.datumindiening ? (
                      <TextFormat type="date" value={mulderfeit.datumindiening} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{mulderfeit.dienstcd}</td>
                  <td>{mulderfeit.organisatie}</td>
                  <td>{mulderfeit.overtreding}</td>
                  <td>{mulderfeit.parkeertarief}</td>
                  <td>{mulderfeit.redenseponeren}</td>
                  <td>{mulderfeit.vorderingnummer}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/mulderfeit/${mulderfeit.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/mulderfeit/${mulderfeit.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/mulderfeit/${mulderfeit.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Mulderfeits found</div>
        )}
      </div>
    </div>
  );
};

export default Mulderfeit;
