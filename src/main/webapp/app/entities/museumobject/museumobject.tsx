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

import { getEntities } from './museumobject.reducer';

export const Museumobject = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const museumobjectList = useAppSelector(state => state.museumobject.entities);
  const loading = useAppSelector(state => state.museumobject.loading);

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
      <h2 id="museumobject-heading" data-cy="MuseumobjectHeading">
        Museumobjects
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/museumobject/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Museumobject
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {museumobjectList && museumobjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('afmeting')}>
                  Afmeting <FontAwesomeIcon icon={getSortIconByFieldName('afmeting')} />
                </th>
                <th className="hand" onClick={sort('bezittot')}>
                  Bezittot <FontAwesomeIcon icon={getSortIconByFieldName('bezittot')} />
                </th>
                <th className="hand" onClick={sort('bezitvanaf')}>
                  Bezitvanaf <FontAwesomeIcon icon={getSortIconByFieldName('bezitvanaf')} />
                </th>
                <th className="hand" onClick={sort('medium')}>
                  Medium <FontAwesomeIcon icon={getSortIconByFieldName('medium')} />
                </th>
                <th className="hand" onClick={sort('verkrijging')}>
                  Verkrijging <FontAwesomeIcon icon={getSortIconByFieldName('verkrijging')} />
                </th>
                <th>
                  Betreft Bruikleen <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Locatie Standplaats <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Belanghebbende <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Onderdeel Tentoonstelling <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Bevat Collectie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Incident <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {museumobjectList.map((museumobject, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/museumobject/${museumobject.id}`} color="link" size="sm">
                      {museumobject.id}
                    </Button>
                  </td>
                  <td>{museumobject.afmeting}</td>
                  <td>
                    {museumobject.bezittot ? <TextFormat type="date" value={museumobject.bezittot} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {museumobject.bezitvanaf ? (
                      <TextFormat type="date" value={museumobject.bezitvanaf} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{museumobject.medium}</td>
                  <td>{museumobject.verkrijging}</td>
                  <td>
                    {museumobject.betreftBruikleen ? (
                      <Link to={`/bruikleen/${museumobject.betreftBruikleen.id}`}>{museumobject.betreftBruikleen.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {museumobject.locatieStandplaats ? (
                      <Link to={`/standplaats/${museumobject.locatieStandplaats.id}`}>{museumobject.locatieStandplaats.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {museumobject.heeftBelanghebbendes
                      ? museumobject.heeftBelanghebbendes.map((val, j) => (
                          <span key={j}>
                            <Link to={`/belanghebbende/${val.id}`}>{val.id}</Link>
                            {j === museumobject.heeftBelanghebbendes.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {museumobject.onderdeelTentoonstellings
                      ? museumobject.onderdeelTentoonstellings.map((val, j) => (
                          <span key={j}>
                            <Link to={`/tentoonstelling/${val.id}`}>{val.id}</Link>
                            {j === museumobject.onderdeelTentoonstellings.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {museumobject.bevatCollecties
                      ? museumobject.bevatCollecties.map((val, j) => (
                          <span key={j}>
                            <Link to={`/collectie/${val.id}`}>{val.id}</Link>
                            {j === museumobject.bevatCollecties.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {museumobject.betreftIncidents
                      ? museumobject.betreftIncidents.map((val, j) => (
                          <span key={j}>
                            <Link to={`/incident/${val.id}`}>{val.id}</Link>
                            {j === museumobject.betreftIncidents.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/museumobject/${museumobject.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/museumobject/${museumobject.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/museumobject/${museumobject.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Museumobjects found</div>
        )}
      </div>
    </div>
  );
};

export default Museumobject;
