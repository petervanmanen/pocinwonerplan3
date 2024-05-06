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

import { getEntities } from './aanbesteding.reducer';

export const Aanbesteding = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const aanbestedingList = useAppSelector(state => state.aanbesteding.entities);
  const loading = useAppSelector(state => state.aanbesteding.loading);

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
      <h2 id="aanbesteding-heading" data-cy="AanbestedingHeading">
        Aanbestedings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/aanbesteding/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Aanbesteding
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {aanbestedingList && aanbestedingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumpublicatie')}>
                  Datumpublicatie <FontAwesomeIcon icon={getSortIconByFieldName('datumpublicatie')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('digitaal')}>
                  Digitaal <FontAwesomeIcon icon={getSortIconByFieldName('digitaal')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('procedure')}>
                  Procedure <FontAwesomeIcon icon={getSortIconByFieldName('procedure')} />
                </th>
                <th className="hand" onClick={sort('referentienummer')}>
                  Referentienummer <FontAwesomeIcon icon={getSortIconByFieldName('referentienummer')} />
                </th>
                <th className="hand" onClick={sort('scoremaximaal')}>
                  Scoremaximaal <FontAwesomeIcon icon={getSortIconByFieldName('scoremaximaal')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('tendernedkenmerk')}>
                  Tendernedkenmerk <FontAwesomeIcon icon={getSortIconByFieldName('tendernedkenmerk')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('volgendesluiting')}>
                  Volgendesluiting <FontAwesomeIcon icon={getSortIconByFieldName('volgendesluiting')} />
                </th>
                <th>
                  Betreft Zaak <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Mondtuit Gunning <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Procesleider Medewerker <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {aanbestedingList.map((aanbesteding, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/aanbesteding/${aanbesteding.id}`} color="link" size="sm">
                      {aanbesteding.id}
                    </Button>
                  </td>
                  <td>{aanbesteding.datumpublicatie}</td>
                  <td>
                    {aanbesteding.datumstart ? (
                      <TextFormat type="date" value={aanbesteding.datumstart} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{aanbesteding.digitaal}</td>
                  <td>{aanbesteding.naam}</td>
                  <td>{aanbesteding.procedure}</td>
                  <td>{aanbesteding.referentienummer}</td>
                  <td>{aanbesteding.scoremaximaal}</td>
                  <td>{aanbesteding.status}</td>
                  <td>{aanbesteding.tendernedkenmerk}</td>
                  <td>{aanbesteding.type}</td>
                  <td>{aanbesteding.volgendesluiting}</td>
                  <td>
                    {aanbesteding.betreftZaak ? <Link to={`/zaak/${aanbesteding.betreftZaak.id}`}>{aanbesteding.betreftZaak.id}</Link> : ''}
                  </td>
                  <td>
                    {aanbesteding.mondtuitGunning ? (
                      <Link to={`/gunning/${aanbesteding.mondtuitGunning.id}`}>{aanbesteding.mondtuitGunning.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {aanbesteding.procesleiderMedewerker ? (
                      <Link to={`/medewerker/${aanbesteding.procesleiderMedewerker.id}`}>{aanbesteding.procesleiderMedewerker.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/aanbesteding/${aanbesteding.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/aanbesteding/${aanbesteding.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/aanbesteding/${aanbesteding.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Aanbestedings found</div>
        )}
      </div>
    </div>
  );
};

export default Aanbesteding;
