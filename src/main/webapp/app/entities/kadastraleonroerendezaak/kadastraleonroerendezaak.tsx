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

import { getEntities } from './kadastraleonroerendezaak.reducer';

export const Kadastraleonroerendezaak = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const kadastraleonroerendezaakList = useAppSelector(state => state.kadastraleonroerendezaak.entities);
  const loading = useAppSelector(state => state.kadastraleonroerendezaak.loading);

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
      <h2 id="kadastraleonroerendezaak-heading" data-cy="KadastraleonroerendezaakHeading">
        Kadastraleonroerendezaaks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/kadastraleonroerendezaak/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Kadastraleonroerendezaak
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {kadastraleonroerendezaakList && kadastraleonroerendezaakList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('empty')}>
                  Empty <FontAwesomeIcon icon={getSortIconByFieldName('empty')} />
                </th>
                <th className="hand" onClick={sort('appartementsrechtvolgnummer')}>
                  Appartementsrechtvolgnummer <FontAwesomeIcon icon={getSortIconByFieldName('appartementsrechtvolgnummer')} />
                </th>
                <th className="hand" onClick={sort('begrenzing')}>
                  Begrenzing <FontAwesomeIcon icon={getSortIconByFieldName('begrenzing')} />
                </th>
                <th className="hand" onClick={sort('cultuurcodeonbebouwd')}>
                  Cultuurcodeonbebouwd <FontAwesomeIcon icon={getSortIconByFieldName('cultuurcodeonbebouwd')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidkadastraleonroerendezaak')}>
                  Datumbegingeldigheidkadastraleonroerendezaak{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidkadastraleonroerendezaak')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidkadastraleonroerendezaak')}>
                  Datumeindegeldigheidkadastraleonroerendezaak{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidkadastraleonroerendezaak')} />
                </th>
                <th className="hand" onClick={sort('identificatie')}>
                  Identificatie <FontAwesomeIcon icon={getSortIconByFieldName('identificatie')} />
                </th>
                <th className="hand" onClick={sort('kadastralegemeente')}>
                  Kadastralegemeente <FontAwesomeIcon icon={getSortIconByFieldName('kadastralegemeente')} />
                </th>
                <th className="hand" onClick={sort('kadastralegemeentecode')}>
                  Kadastralegemeentecode <FontAwesomeIcon icon={getSortIconByFieldName('kadastralegemeentecode')} />
                </th>
                <th className="hand" onClick={sort('koopjaar')}>
                  Koopjaar <FontAwesomeIcon icon={getSortIconByFieldName('koopjaar')} />
                </th>
                <th className="hand" onClick={sort('koopsom')}>
                  Koopsom <FontAwesomeIcon icon={getSortIconByFieldName('koopsom')} />
                </th>
                <th className="hand" onClick={sort('landinrichtingrentebedrag')}>
                  Landinrichtingrentebedrag <FontAwesomeIcon icon={getSortIconByFieldName('landinrichtingrentebedrag')} />
                </th>
                <th className="hand" onClick={sort('landinrichtingrenteeindejaar')}>
                  Landinrichtingrenteeindejaar <FontAwesomeIcon icon={getSortIconByFieldName('landinrichtingrenteeindejaar')} />
                </th>
                <th className="hand" onClick={sort('ligging')}>
                  Ligging <FontAwesomeIcon icon={getSortIconByFieldName('ligging')} />
                </th>
                <th className="hand" onClick={sort('locatieomschrijving')}>
                  Locatieomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('locatieomschrijving')} />
                </th>
                <th className="hand" onClick={sort('oppervlakte')}>
                  Oppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('oppervlakte')} />
                </th>
                <th className="hand" onClick={sort('oud')}>
                  Oud <FontAwesomeIcon icon={getSortIconByFieldName('oud')} />
                </th>
                <th className="hand" onClick={sort('perceelnummer')}>
                  Perceelnummer <FontAwesomeIcon icon={getSortIconByFieldName('perceelnummer')} />
                </th>
                <th className="hand" onClick={sort('sectie')}>
                  Sectie <FontAwesomeIcon icon={getSortIconByFieldName('sectie')} />
                </th>
                <th className="hand" onClick={sort('valutacode')}>
                  Valutacode <FontAwesomeIcon icon={getSortIconByFieldName('valutacode')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {kadastraleonroerendezaakList.map((kadastraleonroerendezaak, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/kadastraleonroerendezaak/${kadastraleonroerendezaak.id}`} color="link" size="sm">
                      {kadastraleonroerendezaak.id}
                    </Button>
                  </td>
                  <td>{kadastraleonroerendezaak.empty}</td>
                  <td>{kadastraleonroerendezaak.appartementsrechtvolgnummer}</td>
                  <td>{kadastraleonroerendezaak.begrenzing}</td>
                  <td>{kadastraleonroerendezaak.cultuurcodeonbebouwd}</td>
                  <td>
                    {kadastraleonroerendezaak.datumbegingeldigheidkadastraleonroerendezaak ? (
                      <TextFormat
                        type="date"
                        value={kadastraleonroerendezaak.datumbegingeldigheidkadastraleonroerendezaak}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>
                    {kadastraleonroerendezaak.datumeindegeldigheidkadastraleonroerendezaak ? (
                      <TextFormat
                        type="date"
                        value={kadastraleonroerendezaak.datumeindegeldigheidkadastraleonroerendezaak}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{kadastraleonroerendezaak.identificatie}</td>
                  <td>{kadastraleonroerendezaak.kadastralegemeente}</td>
                  <td>{kadastraleonroerendezaak.kadastralegemeentecode}</td>
                  <td>{kadastraleonroerendezaak.koopjaar}</td>
                  <td>{kadastraleonroerendezaak.koopsom}</td>
                  <td>{kadastraleonroerendezaak.landinrichtingrentebedrag}</td>
                  <td>{kadastraleonroerendezaak.landinrichtingrenteeindejaar}</td>
                  <td>{kadastraleonroerendezaak.ligging}</td>
                  <td>{kadastraleonroerendezaak.locatieomschrijving}</td>
                  <td>{kadastraleonroerendezaak.oppervlakte}</td>
                  <td>{kadastraleonroerendezaak.oud}</td>
                  <td>{kadastraleonroerendezaak.perceelnummer}</td>
                  <td>{kadastraleonroerendezaak.sectie}</td>
                  <td>{kadastraleonroerendezaak.valutacode}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/kadastraleonroerendezaak/${kadastraleonroerendezaak.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/kadastraleonroerendezaak/${kadastraleonroerendezaak.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/kadastraleonroerendezaak/${kadastraleonroerendezaak.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Kadastraleonroerendezaaks found</div>
        )}
      </div>
    </div>
  );
};

export default Kadastraleonroerendezaak;
