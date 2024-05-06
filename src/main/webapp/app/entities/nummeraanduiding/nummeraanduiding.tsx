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

import { getEntities } from './nummeraanduiding.reducer';

export const Nummeraanduiding = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const nummeraanduidingList = useAppSelector(state => state.nummeraanduiding.entities);
  const loading = useAppSelector(state => state.nummeraanduiding.loading);

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
      <h2 id="nummeraanduiding-heading" data-cy="NummeraanduidingHeading">
        Nummeraanduidings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/nummeraanduiding/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Nummeraanduiding
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {nummeraanduidingList && nummeraanduidingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheid')}>
                  Datumbegingeldigheid <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheid')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheid')}>
                  Datumeindegeldigheid <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheid')} />
                </th>
                <th className="hand" onClick={sort('datumingang')}>
                  Datumingang <FontAwesomeIcon icon={getSortIconByFieldName('datumingang')} />
                </th>
                <th className="hand" onClick={sort('geconstateerd')}>
                  Geconstateerd <FontAwesomeIcon icon={getSortIconByFieldName('geconstateerd')} />
                </th>
                <th className="hand" onClick={sort('geometrie')}>
                  Geometrie <FontAwesomeIcon icon={getSortIconByFieldName('geometrie')} />
                </th>
                <th className="hand" onClick={sort('huisletter')}>
                  Huisletter <FontAwesomeIcon icon={getSortIconByFieldName('huisletter')} />
                </th>
                <th className="hand" onClick={sort('huisnummer')}>
                  Huisnummer <FontAwesomeIcon icon={getSortIconByFieldName('huisnummer')} />
                </th>
                <th className="hand" onClick={sort('huisnummertoevoeging')}>
                  Huisnummertoevoeging <FontAwesomeIcon icon={getSortIconByFieldName('huisnummertoevoeging')} />
                </th>
                <th className="hand" onClick={sort('identificatie')}>
                  Identificatie <FontAwesomeIcon icon={getSortIconByFieldName('identificatie')} />
                </th>
                <th className="hand" onClick={sort('inonderzoek')}>
                  Inonderzoek <FontAwesomeIcon icon={getSortIconByFieldName('inonderzoek')} />
                </th>
                <th className="hand" onClick={sort('postcode')}>
                  Postcode <FontAwesomeIcon icon={getSortIconByFieldName('postcode')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('typeadresseerbaarobject')}>
                  Typeadresseerbaarobject <FontAwesomeIcon icon={getSortIconByFieldName('typeadresseerbaarobject')} />
                </th>
                <th className="hand" onClick={sort('versie')}>
                  Versie <FontAwesomeIcon icon={getSortIconByFieldName('versie')} />
                </th>
                <th>
                  Ligtin Woonplaats <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Ligtin Buurt <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Ligtin Gebied <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nummeraanduidingList.map((nummeraanduiding, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/nummeraanduiding/${nummeraanduiding.id}`} color="link" size="sm">
                      {nummeraanduiding.id}
                    </Button>
                  </td>
                  <td>
                    {nummeraanduiding.datumbegingeldigheid ? (
                      <TextFormat type="date" value={nummeraanduiding.datumbegingeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {nummeraanduiding.datumeinde ? (
                      <TextFormat type="date" value={nummeraanduiding.datumeinde} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {nummeraanduiding.datumeindegeldigheid ? (
                      <TextFormat type="date" value={nummeraanduiding.datumeindegeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {nummeraanduiding.datumingang ? (
                      <TextFormat type="date" value={nummeraanduiding.datumingang} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{nummeraanduiding.geconstateerd ? 'true' : 'false'}</td>
                  <td>{nummeraanduiding.geometrie}</td>
                  <td>{nummeraanduiding.huisletter}</td>
                  <td>{nummeraanduiding.huisnummer}</td>
                  <td>{nummeraanduiding.huisnummertoevoeging}</td>
                  <td>{nummeraanduiding.identificatie}</td>
                  <td>{nummeraanduiding.inonderzoek ? 'true' : 'false'}</td>
                  <td>{nummeraanduiding.postcode}</td>
                  <td>{nummeraanduiding.status}</td>
                  <td>{nummeraanduiding.typeadresseerbaarobject}</td>
                  <td>{nummeraanduiding.versie}</td>
                  <td>
                    {nummeraanduiding.ligtinWoonplaats ? (
                      <Link to={`/woonplaats/${nummeraanduiding.ligtinWoonplaats.id}`}>{nummeraanduiding.ligtinWoonplaats.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {nummeraanduiding.ligtinBuurt ? (
                      <Link to={`/buurt/${nummeraanduiding.ligtinBuurt.id}`}>{nummeraanduiding.ligtinBuurt.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {nummeraanduiding.ligtinGebieds
                      ? nummeraanduiding.ligtinGebieds.map((val, j) => (
                          <span key={j}>
                            <Link to={`/gebied/${val.id}`}>{val.id}</Link>
                            {j === nummeraanduiding.ligtinGebieds.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nummeraanduiding/${nummeraanduiding.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/nummeraanduiding/${nummeraanduiding.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/nummeraanduiding/${nummeraanduiding.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Nummeraanduidings found</div>
        )}
      </div>
    </div>
  );
};

export default Nummeraanduiding;
