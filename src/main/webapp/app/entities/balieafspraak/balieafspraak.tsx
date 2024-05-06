import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './balieafspraak.reducer';

export const Balieafspraak = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const balieafspraakList = useAppSelector(state => state.balieafspraak.entities);
  const loading = useAppSelector(state => state.balieafspraak.loading);

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
      <h2 id="balieafspraak-heading" data-cy="BalieafspraakHeading">
        Balieafspraaks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/balieafspraak/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Balieafspraak
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {balieafspraakList && balieafspraakList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('eindtijdgepland')}>
                  Eindtijdgepland <FontAwesomeIcon icon={getSortIconByFieldName('eindtijdgepland')} />
                </th>
                <th className="hand" onClick={sort('notitie')}>
                  Notitie <FontAwesomeIcon icon={getSortIconByFieldName('notitie')} />
                </th>
                <th className="hand" onClick={sort('starttijdgepland')}>
                  Starttijdgepland <FontAwesomeIcon icon={getSortIconByFieldName('starttijdgepland')} />
                </th>
                <th className="hand" onClick={sort('tijdaangemaakt')}>
                  Tijdaangemaakt <FontAwesomeIcon icon={getSortIconByFieldName('tijdaangemaakt')} />
                </th>
                <th className="hand" onClick={sort('tijdsduurgepland')}>
                  Tijdsduurgepland <FontAwesomeIcon icon={getSortIconByFieldName('tijdsduurgepland')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th className="hand" onClick={sort('wachttijdnastartafspraak')}>
                  Wachttijdnastartafspraak <FontAwesomeIcon icon={getSortIconByFieldName('wachttijdnastartafspraak')} />
                </th>
                <th className="hand" onClick={sort('wachttijdtotaal')}>
                  Wachttijdtotaal <FontAwesomeIcon icon={getSortIconByFieldName('wachttijdtotaal')} />
                </th>
                <th className="hand" onClick={sort('wachttijdvoorstartafspraak')}>
                  Wachttijdvoorstartafspraak <FontAwesomeIcon icon={getSortIconByFieldName('wachttijdvoorstartafspraak')} />
                </th>
                <th className="hand" onClick={sort('werkelijketijdsduur')}>
                  Werkelijketijdsduur <FontAwesomeIcon icon={getSortIconByFieldName('werkelijketijdsduur')} />
                </th>
                <th>
                  Mondtuitin Klantcontact <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Afspraakstatus <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Met Medewerker <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftbetrekkingop Zaak <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {balieafspraakList.map((balieafspraak, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/balieafspraak/${balieafspraak.id}`} color="link" size="sm">
                      {balieafspraak.id}
                    </Button>
                  </td>
                  <td>{balieafspraak.eindtijdgepland}</td>
                  <td>{balieafspraak.notitie}</td>
                  <td>{balieafspraak.starttijdgepland}</td>
                  <td>{balieafspraak.tijdaangemaakt}</td>
                  <td>{balieafspraak.tijdsduurgepland}</td>
                  <td>{balieafspraak.toelichting}</td>
                  <td>{balieafspraak.wachttijdnastartafspraak}</td>
                  <td>{balieafspraak.wachttijdtotaal}</td>
                  <td>{balieafspraak.wachttijdvoorstartafspraak}</td>
                  <td>{balieafspraak.werkelijketijdsduur}</td>
                  <td>
                    {balieafspraak.mondtuitinKlantcontact ? (
                      <Link to={`/klantcontact/${balieafspraak.mondtuitinKlantcontact.id}`}>{balieafspraak.mondtuitinKlantcontact.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {balieafspraak.heeftAfspraakstatus ? (
                      <Link to={`/afspraakstatus/${balieafspraak.heeftAfspraakstatus.id}`}>{balieafspraak.heeftAfspraakstatus.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {balieafspraak.metMedewerker ? (
                      <Link to={`/medewerker/${balieafspraak.metMedewerker.id}`}>{balieafspraak.metMedewerker.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {balieafspraak.heeftbetrekkingopZaak ? (
                      <Link to={`/zaak/${balieafspraak.heeftbetrekkingopZaak.id}`}>{balieafspraak.heeftbetrekkingopZaak.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/balieafspraak/${balieafspraak.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/balieafspraak/${balieafspraak.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/balieafspraak/${balieafspraak.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Balieafspraaks found</div>
        )}
      </div>
    </div>
  );
};

export default Balieafspraak;
