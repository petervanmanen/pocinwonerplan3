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

import { getEntities } from './vthmelding.reducer';

export const Vthmelding = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vthmeldingList = useAppSelector(state => state.vthmelding.entities);
  const loading = useAppSelector(state => state.vthmelding.loading);

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
      <h2 id="vthmelding-heading" data-cy="VthmeldingHeading">
        Vthmeldings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vthmelding/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vthmelding
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vthmeldingList && vthmeldingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('activiteit')}>
                  Activiteit <FontAwesomeIcon icon={getSortIconByFieldName('activiteit')} />
                </th>
                <th className="hand" onClick={sort('beoordeling')}>
                  Beoordeling <FontAwesomeIcon icon={getSortIconByFieldName('beoordeling')} />
                </th>
                <th className="hand" onClick={sort('datumseponering')}>
                  Datumseponering <FontAwesomeIcon icon={getSortIconByFieldName('datumseponering')} />
                </th>
                <th className="hand" onClick={sort('datumtijdtot')}>
                  Datumtijdtot <FontAwesomeIcon icon={getSortIconByFieldName('datumtijdtot')} />
                </th>
                <th className="hand" onClick={sort('geseponeerd')}>
                  Geseponeerd <FontAwesomeIcon icon={getSortIconByFieldName('geseponeerd')} />
                </th>
                <th className="hand" onClick={sort('locatie')}>
                  Locatie <FontAwesomeIcon icon={getSortIconByFieldName('locatie')} />
                </th>
                <th className="hand" onClick={sort('organisatieonderdeel')}>
                  Organisatieonderdeel <FontAwesomeIcon icon={getSortIconByFieldName('organisatieonderdeel')} />
                </th>
                <th className="hand" onClick={sort('overtredingscode')}>
                  Overtredingscode <FontAwesomeIcon icon={getSortIconByFieldName('overtredingscode')} />
                </th>
                <th className="hand" onClick={sort('overtredingsgroep')}>
                  Overtredingsgroep <FontAwesomeIcon icon={getSortIconByFieldName('overtredingsgroep')} />
                </th>
                <th className="hand" onClick={sort('referentienummer')}>
                  Referentienummer <FontAwesomeIcon icon={getSortIconByFieldName('referentienummer')} />
                </th>
                <th className="hand" onClick={sort('resultaat')}>
                  Resultaat <FontAwesomeIcon icon={getSortIconByFieldName('resultaat')} />
                </th>
                <th className="hand" onClick={sort('soortvthmelding')}>
                  Soortvthmelding <FontAwesomeIcon icon={getSortIconByFieldName('soortvthmelding')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('straatnaam')}>
                  Straatnaam <FontAwesomeIcon icon={getSortIconByFieldName('straatnaam')} />
                </th>
                <th className="hand" onClick={sort('taaktype')}>
                  Taaktype <FontAwesomeIcon icon={getSortIconByFieldName('taaktype')} />
                </th>
                <th className="hand" onClick={sort('zaaknummer')}>
                  Zaaknummer <FontAwesomeIcon icon={getSortIconByFieldName('zaaknummer')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vthmeldingList.map((vthmelding, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vthmelding/${vthmelding.id}`} color="link" size="sm">
                      {vthmelding.id}
                    </Button>
                  </td>
                  <td>{vthmelding.activiteit}</td>
                  <td>{vthmelding.beoordeling}</td>
                  <td>
                    {vthmelding.datumseponering ? (
                      <TextFormat type="date" value={vthmelding.datumseponering} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{vthmelding.datumtijdtot}</td>
                  <td>{vthmelding.geseponeerd}</td>
                  <td>{vthmelding.locatie}</td>
                  <td>{vthmelding.organisatieonderdeel}</td>
                  <td>{vthmelding.overtredingscode}</td>
                  <td>{vthmelding.overtredingsgroep}</td>
                  <td>{vthmelding.referentienummer}</td>
                  <td>{vthmelding.resultaat}</td>
                  <td>{vthmelding.soortvthmelding}</td>
                  <td>{vthmelding.status}</td>
                  <td>{vthmelding.straatnaam}</td>
                  <td>{vthmelding.taaktype}</td>
                  <td>{vthmelding.zaaknummer}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vthmelding/${vthmelding.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/vthmelding/${vthmelding.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vthmelding/${vthmelding.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vthmeldings found</div>
        )}
      </div>
    </div>
  );
};

export default Vthmelding;
