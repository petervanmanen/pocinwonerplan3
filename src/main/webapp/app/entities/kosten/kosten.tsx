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

import { getEntities } from './kosten.reducer';

export const Kosten = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const kostenList = useAppSelector(state => state.kosten.entities);
  const loading = useAppSelector(state => state.kosten.loading);

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
      <h2 id="kosten-heading" data-cy="KostenHeading">
        Kostens
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/kosten/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Kosten
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {kostenList && kostenList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aangemaaktdoor')}>
                  Aangemaaktdoor <FontAwesomeIcon icon={getSortIconByFieldName('aangemaaktdoor')} />
                </th>
                <th className="hand" onClick={sort('aantal')}>
                  Aantal <FontAwesomeIcon icon={getSortIconByFieldName('aantal')} />
                </th>
                <th className="hand" onClick={sort('bedrag')}>
                  Bedrag <FontAwesomeIcon icon={getSortIconByFieldName('bedrag')} />
                </th>
                <th className="hand" onClick={sort('bedragtotaal')}>
                  Bedragtotaal <FontAwesomeIcon icon={getSortIconByFieldName('bedragtotaal')} />
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
                <th className="hand" onClick={sort('geaccordeerd')}>
                  Geaccordeerd <FontAwesomeIcon icon={getSortIconByFieldName('geaccordeerd')} />
                </th>
                <th className="hand" onClick={sort('gefactureerdop')}>
                  Gefactureerdop <FontAwesomeIcon icon={getSortIconByFieldName('gefactureerdop')} />
                </th>
                <th className="hand" onClick={sort('gemuteerddoor')}>
                  Gemuteerddoor <FontAwesomeIcon icon={getSortIconByFieldName('gemuteerddoor')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('opbasisvangrondslag')}>
                  Opbasisvangrondslag <FontAwesomeIcon icon={getSortIconByFieldName('opbasisvangrondslag')} />
                </th>
                <th className="hand" onClick={sort('tarief')}>
                  Tarief <FontAwesomeIcon icon={getSortIconByFieldName('tarief')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('vastgesteldbedrag')}>
                  Vastgesteldbedrag <FontAwesomeIcon icon={getSortIconByFieldName('vastgesteldbedrag')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {kostenList.map((kosten, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/kosten/${kosten.id}`} color="link" size="sm">
                      {kosten.id}
                    </Button>
                  </td>
                  <td>{kosten.aangemaaktdoor}</td>
                  <td>{kosten.aantal}</td>
                  <td>{kosten.bedrag}</td>
                  <td>{kosten.bedragtotaal}</td>
                  <td>
                    {kosten.datumaanmaak ? <TextFormat type="date" value={kosten.datumaanmaak} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {kosten.datummutatie ? <TextFormat type="date" value={kosten.datummutatie} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{kosten.eenheid}</td>
                  <td>{kosten.geaccordeerd}</td>
                  <td>
                    {kosten.gefactureerdop ? <TextFormat type="date" value={kosten.gefactureerdop} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{kosten.gemuteerddoor}</td>
                  <td>{kosten.naam}</td>
                  <td>{kosten.omschrijving}</td>
                  <td>{kosten.opbasisvangrondslag}</td>
                  <td>{kosten.tarief}</td>
                  <td>{kosten.type}</td>
                  <td>{kosten.vastgesteldbedrag}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/kosten/${kosten.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/kosten/${kosten.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/kosten/${kosten.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Kostens found</div>
        )}
      </div>
    </div>
  );
};

export default Kosten;
