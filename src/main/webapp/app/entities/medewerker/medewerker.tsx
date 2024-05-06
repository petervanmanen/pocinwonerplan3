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

import { getEntities } from './medewerker.reducer';

export const Medewerker = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const medewerkerList = useAppSelector(state => state.medewerker.entities);
  const loading = useAppSelector(state => state.medewerker.loading);

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
      <h2 id="medewerker-heading" data-cy="MedewerkerHeading">
        Medewerkers
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/medewerker/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Medewerker
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {medewerkerList && medewerkerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('achternaam')}>
                  Achternaam <FontAwesomeIcon icon={getSortIconByFieldName('achternaam')} />
                </th>
                <th className="hand" onClick={sort('datumindienst')}>
                  Datumindienst <FontAwesomeIcon icon={getSortIconByFieldName('datumindienst')} />
                </th>
                <th className="hand" onClick={sort('datumuitdienst')}>
                  Datumuitdienst <FontAwesomeIcon icon={getSortIconByFieldName('datumuitdienst')} />
                </th>
                <th className="hand" onClick={sort('emailadres')}>
                  Emailadres <FontAwesomeIcon icon={getSortIconByFieldName('emailadres')} />
                </th>
                <th className="hand" onClick={sort('extern')}>
                  Extern <FontAwesomeIcon icon={getSortIconByFieldName('extern')} />
                </th>
                <th className="hand" onClick={sort('functie')}>
                  Functie <FontAwesomeIcon icon={getSortIconByFieldName('functie')} />
                </th>
                <th className="hand" onClick={sort('geslachtsaanduiding')}>
                  Geslachtsaanduiding <FontAwesomeIcon icon={getSortIconByFieldName('geslachtsaanduiding')} />
                </th>
                <th className="hand" onClick={sort('medewerkeridentificatie')}>
                  Medewerkeridentificatie <FontAwesomeIcon icon={getSortIconByFieldName('medewerkeridentificatie')} />
                </th>
                <th className="hand" onClick={sort('medewerkertoelichting')}>
                  Medewerkertoelichting <FontAwesomeIcon icon={getSortIconByFieldName('medewerkertoelichting')} />
                </th>
                <th className="hand" onClick={sort('roepnaam')}>
                  Roepnaam <FontAwesomeIcon icon={getSortIconByFieldName('roepnaam')} />
                </th>
                <th className="hand" onClick={sort('telefoonnummer')}>
                  Telefoonnummer <FontAwesomeIcon icon={getSortIconByFieldName('telefoonnummer')} />
                </th>
                <th className="hand" onClick={sort('voorletters')}>
                  Voorletters <FontAwesomeIcon icon={getSortIconByFieldName('voorletters')} />
                </th>
                <th className="hand" onClick={sort('voorvoegselachternaam')}>
                  Voorvoegselachternaam <FontAwesomeIcon icon={getSortIconByFieldName('voorvoegselachternaam')} />
                </th>
                <th>
                  Geleverdvia Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Rollen Applicatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Afhandelendmedewerker Zaak <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {medewerkerList.map((medewerker, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/medewerker/${medewerker.id}`} color="link" size="sm">
                      {medewerker.id}
                    </Button>
                  </td>
                  <td>{medewerker.achternaam}</td>
                  <td>
                    {medewerker.datumindienst ? (
                      <TextFormat type="date" value={medewerker.datumindienst} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{medewerker.datumuitdienst}</td>
                  <td>{medewerker.emailadres}</td>
                  <td>{medewerker.extern}</td>
                  <td>{medewerker.functie}</td>
                  <td>{medewerker.geslachtsaanduiding}</td>
                  <td>{medewerker.medewerkeridentificatie}</td>
                  <td>{medewerker.medewerkertoelichting}</td>
                  <td>{medewerker.roepnaam}</td>
                  <td>{medewerker.telefoonnummer}</td>
                  <td>{medewerker.voorletters}</td>
                  <td>{medewerker.voorvoegselachternaam}</td>
                  <td>
                    {medewerker.geleverdviaLeverancier ? (
                      <Link to={`/leverancier/${medewerker.geleverdviaLeverancier.id}`}>{medewerker.geleverdviaLeverancier.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {medewerker.rollenApplicaties
                      ? medewerker.rollenApplicaties.map((val, j) => (
                          <span key={j}>
                            <Link to={`/applicatie/${val.id}`}>{val.id}</Link>
                            {j === medewerker.rollenApplicaties.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {medewerker.afhandelendmedewerkerZaaks
                      ? medewerker.afhandelendmedewerkerZaaks.map((val, j) => (
                          <span key={j}>
                            <Link to={`/zaak/${val.id}`}>{val.id}</Link>
                            {j === medewerker.afhandelendmedewerkerZaaks.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/medewerker/${medewerker.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/medewerker/${medewerker.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/medewerker/${medewerker.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Medewerkers found</div>
        )}
      </div>
    </div>
  );
};

export default Medewerker;
