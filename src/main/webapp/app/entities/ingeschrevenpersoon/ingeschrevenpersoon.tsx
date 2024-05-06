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

import { getEntities } from './ingeschrevenpersoon.reducer';

export const Ingeschrevenpersoon = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const ingeschrevenpersoonList = useAppSelector(state => state.ingeschrevenpersoon.entities);
  const loading = useAppSelector(state => state.ingeschrevenpersoon.loading);

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
      <h2 id="ingeschrevenpersoon-heading" data-cy="IngeschrevenpersoonHeading">
        Ingeschrevenpersoons
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/ingeschrevenpersoon/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Ingeschrevenpersoon
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ingeschrevenpersoonList && ingeschrevenpersoonList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adresherkomst')}>
                  Adresherkomst <FontAwesomeIcon icon={getSortIconByFieldName('adresherkomst')} />
                </th>
                <th className="hand" onClick={sort('anummer')}>
                  Anummer <FontAwesomeIcon icon={getSortIconByFieldName('anummer')} />
                </th>
                <th className="hand" onClick={sort('beschrijvinglocatie')}>
                  Beschrijvinglocatie <FontAwesomeIcon icon={getSortIconByFieldName('beschrijvinglocatie')} />
                </th>
                <th className="hand" onClick={sort('buitenlandsreisdocument')}>
                  Buitenlandsreisdocument <FontAwesomeIcon icon={getSortIconByFieldName('buitenlandsreisdocument')} />
                </th>
                <th className="hand" onClick={sort('burgerlijkestaat')}>
                  Burgerlijkestaat <FontAwesomeIcon icon={getSortIconByFieldName('burgerlijkestaat')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidverblijfplaats')}>
                  Datumbegingeldigheidverblijfplaats <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidverblijfplaats')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidverblijfsplaats')}>
                  Datumeindegeldigheidverblijfsplaats{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidverblijfsplaats')} />
                </th>
                <th className="hand" onClick={sort('datuminschrijvinggemeente')}>
                  Datuminschrijvinggemeente <FontAwesomeIcon icon={getSortIconByFieldName('datuminschrijvinggemeente')} />
                </th>
                <th className="hand" onClick={sort('datumopschortingbijhouding')}>
                  Datumopschortingbijhouding <FontAwesomeIcon icon={getSortIconByFieldName('datumopschortingbijhouding')} />
                </th>
                <th className="hand" onClick={sort('datumvertrekuitnederland')}>
                  Datumvertrekuitnederland <FontAwesomeIcon icon={getSortIconByFieldName('datumvertrekuitnederland')} />
                </th>
                <th className="hand" onClick={sort('datumvestigingnederland')}>
                  Datumvestigingnederland <FontAwesomeIcon icon={getSortIconByFieldName('datumvestigingnederland')} />
                </th>
                <th className="hand" onClick={sort('gemeentevaninschrijving')}>
                  Gemeentevaninschrijving <FontAwesomeIcon icon={getSortIconByFieldName('gemeentevaninschrijving')} />
                </th>
                <th className="hand" onClick={sort('gezinsrelatie')}>
                  Gezinsrelatie <FontAwesomeIcon icon={getSortIconByFieldName('gezinsrelatie')} />
                </th>
                <th className="hand" onClick={sort('indicatiegeheim')}>
                  Indicatiegeheim <FontAwesomeIcon icon={getSortIconByFieldName('indicatiegeheim')} />
                </th>
                <th className="hand" onClick={sort('ingezetene')}>
                  Ingezetene <FontAwesomeIcon icon={getSortIconByFieldName('ingezetene')} />
                </th>
                <th className="hand" onClick={sort('landwaarnaarvertrokken')}>
                  Landwaarnaarvertrokken <FontAwesomeIcon icon={getSortIconByFieldName('landwaarnaarvertrokken')} />
                </th>
                <th className="hand" onClick={sort('landwaarvandaaningeschreven')}>
                  Landwaarvandaaningeschreven <FontAwesomeIcon icon={getSortIconByFieldName('landwaarvandaaningeschreven')} />
                </th>
                <th className="hand" onClick={sort('ouder1')}>
                  Ouder 1 <FontAwesomeIcon icon={getSortIconByFieldName('ouder1')} />
                </th>
                <th className="hand" onClick={sort('ouder2')}>
                  Ouder 2 <FontAwesomeIcon icon={getSortIconByFieldName('ouder2')} />
                </th>
                <th className="hand" onClick={sort('partnerid')}>
                  Partnerid <FontAwesomeIcon icon={getSortIconByFieldName('partnerid')} />
                </th>
                <th className="hand" onClick={sort('redeneindebewoning')}>
                  Redeneindebewoning <FontAwesomeIcon icon={getSortIconByFieldName('redeneindebewoning')} />
                </th>
                <th className="hand" onClick={sort('redenopschortingbijhouding')}>
                  Redenopschortingbijhouding <FontAwesomeIcon icon={getSortIconByFieldName('redenopschortingbijhouding')} />
                </th>
                <th className="hand" onClick={sort('signaleringreisdocument')}>
                  Signaleringreisdocument <FontAwesomeIcon icon={getSortIconByFieldName('signaleringreisdocument')} />
                </th>
                <th className="hand" onClick={sort('verblijfstitel')}>
                  Verblijfstitel <FontAwesomeIcon icon={getSortIconByFieldName('verblijfstitel')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ingeschrevenpersoonList.map((ingeschrevenpersoon, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/ingeschrevenpersoon/${ingeschrevenpersoon.id}`} color="link" size="sm">
                      {ingeschrevenpersoon.id}
                    </Button>
                  </td>
                  <td>{ingeschrevenpersoon.adresherkomst}</td>
                  <td>{ingeschrevenpersoon.anummer}</td>
                  <td>{ingeschrevenpersoon.beschrijvinglocatie}</td>
                  <td>{ingeschrevenpersoon.buitenlandsreisdocument}</td>
                  <td>{ingeschrevenpersoon.burgerlijkestaat}</td>
                  <td>{ingeschrevenpersoon.datumbegingeldigheidverblijfplaats}</td>
                  <td>
                    {ingeschrevenpersoon.datumeindegeldigheidverblijfsplaats ? (
                      <TextFormat
                        type="date"
                        value={ingeschrevenpersoon.datumeindegeldigheidverblijfsplaats}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{ingeschrevenpersoon.datuminschrijvinggemeente}</td>
                  <td>{ingeschrevenpersoon.datumopschortingbijhouding}</td>
                  <td>{ingeschrevenpersoon.datumvertrekuitnederland}</td>
                  <td>{ingeschrevenpersoon.datumvestigingnederland}</td>
                  <td>{ingeschrevenpersoon.gemeentevaninschrijving}</td>
                  <td>{ingeschrevenpersoon.gezinsrelatie}</td>
                  <td>{ingeschrevenpersoon.indicatiegeheim}</td>
                  <td>{ingeschrevenpersoon.ingezetene}</td>
                  <td>{ingeschrevenpersoon.landwaarnaarvertrokken}</td>
                  <td>{ingeschrevenpersoon.landwaarvandaaningeschreven}</td>
                  <td>{ingeschrevenpersoon.ouder1}</td>
                  <td>{ingeschrevenpersoon.ouder2}</td>
                  <td>{ingeschrevenpersoon.partnerid}</td>
                  <td>{ingeschrevenpersoon.redeneindebewoning}</td>
                  <td>{ingeschrevenpersoon.redenopschortingbijhouding}</td>
                  <td>{ingeschrevenpersoon.signaleringreisdocument}</td>
                  <td>{ingeschrevenpersoon.verblijfstitel}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/ingeschrevenpersoon/${ingeschrevenpersoon.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/ingeschrevenpersoon/${ingeschrevenpersoon.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/ingeschrevenpersoon/${ingeschrevenpersoon.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Ingeschrevenpersoons found</div>
        )}
      </div>
    </div>
  );
};

export default Ingeschrevenpersoon;
