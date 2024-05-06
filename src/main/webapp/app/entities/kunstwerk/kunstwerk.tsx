import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './kunstwerk.reducer';

export const Kunstwerk = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const kunstwerkList = useAppSelector(state => state.kunstwerk.entities);
  const loading = useAppSelector(state => state.kunstwerk.loading);

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
      <h2 id="kunstwerk-heading" data-cy="KunstwerkHeading">
        Kunstwerks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/kunstwerk/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Kunstwerk
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {kunstwerkList && kunstwerkList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanleghoogte')}>
                  Aanleghoogte <FontAwesomeIcon icon={getSortIconByFieldName('aanleghoogte')} />
                </th>
                <th className="hand" onClick={sort('antigraffitivoorziening')}>
                  Antigraffitivoorziening <FontAwesomeIcon icon={getSortIconByFieldName('antigraffitivoorziening')} />
                </th>
                <th className="hand" onClick={sort('bereikbaarheid')}>
                  Bereikbaarheid <FontAwesomeIcon icon={getSortIconByFieldName('bereikbaarheid')} />
                </th>
                <th className="hand" onClick={sort('breedte')}>
                  Breedte <FontAwesomeIcon icon={getSortIconByFieldName('breedte')} />
                </th>
                <th className="hand" onClick={sort('constructietype')}>
                  Constructietype <FontAwesomeIcon icon={getSortIconByFieldName('constructietype')} />
                </th>
                <th className="hand" onClick={sort('gewicht')}>
                  Gewicht <FontAwesomeIcon icon={getSortIconByFieldName('gewicht')} />
                </th>
                <th className="hand" onClick={sort('hoogte')}>
                  Hoogte <FontAwesomeIcon icon={getSortIconByFieldName('hoogte')} />
                </th>
                <th className="hand" onClick={sort('installateur')}>
                  Installateur <FontAwesomeIcon icon={getSortIconByFieldName('installateur')} />
                </th>
                <th className="hand" onClick={sort('jaarconserveren')}>
                  Jaarconserveren <FontAwesomeIcon icon={getSortIconByFieldName('jaarconserveren')} />
                </th>
                <th className="hand" onClick={sort('jaaronderhouduitgevoerd')}>
                  Jaaronderhouduitgevoerd <FontAwesomeIcon icon={getSortIconByFieldName('jaaronderhouduitgevoerd')} />
                </th>
                <th className="hand" onClick={sort('jaarrenovatie')}>
                  Jaarrenovatie <FontAwesomeIcon icon={getSortIconByFieldName('jaarrenovatie')} />
                </th>
                <th className="hand" onClick={sort('jaarvervanging')}>
                  Jaarvervanging <FontAwesomeIcon icon={getSortIconByFieldName('jaarvervanging')} />
                </th>
                <th className="hand" onClick={sort('kilometreringbegin')}>
                  Kilometreringbegin <FontAwesomeIcon icon={getSortIconByFieldName('kilometreringbegin')} />
                </th>
                <th className="hand" onClick={sort('kilometreringeinde')}>
                  Kilometreringeinde <FontAwesomeIcon icon={getSortIconByFieldName('kilometreringeinde')} />
                </th>
                <th className="hand" onClick={sort('kleur')}>
                  Kleur <FontAwesomeIcon icon={getSortIconByFieldName('kleur')} />
                </th>
                <th className="hand" onClick={sort('kunstwerkbereikbaarheidplus')}>
                  Kunstwerkbereikbaarheidplus <FontAwesomeIcon icon={getSortIconByFieldName('kunstwerkbereikbaarheidplus')} />
                </th>
                <th className="hand" onClick={sort('kunstwerkmateriaal')}>
                  Kunstwerkmateriaal <FontAwesomeIcon icon={getSortIconByFieldName('kunstwerkmateriaal')} />
                </th>
                <th className="hand" onClick={sort('kwaliteitsniveauactueel')}>
                  Kwaliteitsniveauactueel <FontAwesomeIcon icon={getSortIconByFieldName('kwaliteitsniveauactueel')} />
                </th>
                <th className="hand" onClick={sort('kwaliteitsniveaugewenst')}>
                  Kwaliteitsniveaugewenst <FontAwesomeIcon icon={getSortIconByFieldName('kwaliteitsniveaugewenst')} />
                </th>
                <th className="hand" onClick={sort('lengte')}>
                  Lengte <FontAwesomeIcon icon={getSortIconByFieldName('lengte')} />
                </th>
                <th className="hand" onClick={sort('leverancier')}>
                  Leverancier <FontAwesomeIcon icon={getSortIconByFieldName('leverancier')} />
                </th>
                <th className="hand" onClick={sort('looprichel')}>
                  Looprichel <FontAwesomeIcon icon={getSortIconByFieldName('looprichel')} />
                </th>
                <th className="hand" onClick={sort('minimumconditiescore')}>
                  Minimumconditiescore <FontAwesomeIcon icon={getSortIconByFieldName('minimumconditiescore')} />
                </th>
                <th className="hand" onClick={sort('monument')}>
                  Monument <FontAwesomeIcon icon={getSortIconByFieldName('monument')} />
                </th>
                <th className="hand" onClick={sort('monumentnummer')}>
                  Monumentnummer <FontAwesomeIcon icon={getSortIconByFieldName('monumentnummer')} />
                </th>
                <th className="hand" onClick={sort('eobjectnaam')}>
                  Eobjectnaam <FontAwesomeIcon icon={getSortIconByFieldName('eobjectnaam')} />
                </th>
                <th className="hand" onClick={sort('eobjectnummer')}>
                  Eobjectnummer <FontAwesomeIcon icon={getSortIconByFieldName('eobjectnummer')} />
                </th>
                <th className="hand" onClick={sort('onderhoudsregime')}>
                  Onderhoudsregime <FontAwesomeIcon icon={getSortIconByFieldName('onderhoudsregime')} />
                </th>
                <th className="hand" onClick={sort('oppervlakte')}>
                  Oppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('oppervlakte')} />
                </th>
                <th className="hand" onClick={sort('orientatie')}>
                  Orientatie <FontAwesomeIcon icon={getSortIconByFieldName('orientatie')} />
                </th>
                <th className="hand" onClick={sort('technischelevensduur')}>
                  Technischelevensduur <FontAwesomeIcon icon={getSortIconByFieldName('technischelevensduur')} />
                </th>
                <th className="hand" onClick={sort('typefundering')}>
                  Typefundering <FontAwesomeIcon icon={getSortIconByFieldName('typefundering')} />
                </th>
                <th className="hand" onClick={sort('typemonument')}>
                  Typemonument <FontAwesomeIcon icon={getSortIconByFieldName('typemonument')} />
                </th>
                <th className="hand" onClick={sort('vervangingswaarde')}>
                  Vervangingswaarde <FontAwesomeIcon icon={getSortIconByFieldName('vervangingswaarde')} />
                </th>
                <th className="hand" onClick={sort('wegnummer')}>
                  Wegnummer <FontAwesomeIcon icon={getSortIconByFieldName('wegnummer')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {kunstwerkList.map((kunstwerk, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/kunstwerk/${kunstwerk.id}`} color="link" size="sm">
                      {kunstwerk.id}
                    </Button>
                  </td>
                  <td>{kunstwerk.aanleghoogte}</td>
                  <td>{kunstwerk.antigraffitivoorziening ? 'true' : 'false'}</td>
                  <td>{kunstwerk.bereikbaarheid}</td>
                  <td>{kunstwerk.breedte}</td>
                  <td>{kunstwerk.constructietype}</td>
                  <td>{kunstwerk.gewicht}</td>
                  <td>{kunstwerk.hoogte}</td>
                  <td>{kunstwerk.installateur}</td>
                  <td>{kunstwerk.jaarconserveren}</td>
                  <td>{kunstwerk.jaaronderhouduitgevoerd}</td>
                  <td>{kunstwerk.jaarrenovatie}</td>
                  <td>{kunstwerk.jaarvervanging}</td>
                  <td>{kunstwerk.kilometreringbegin}</td>
                  <td>{kunstwerk.kilometreringeinde}</td>
                  <td>{kunstwerk.kleur}</td>
                  <td>{kunstwerk.kunstwerkbereikbaarheidplus}</td>
                  <td>{kunstwerk.kunstwerkmateriaal}</td>
                  <td>{kunstwerk.kwaliteitsniveauactueel}</td>
                  <td>{kunstwerk.kwaliteitsniveaugewenst}</td>
                  <td>{kunstwerk.lengte}</td>
                  <td>{kunstwerk.leverancier}</td>
                  <td>{kunstwerk.looprichel ? 'true' : 'false'}</td>
                  <td>{kunstwerk.minimumconditiescore}</td>
                  <td>{kunstwerk.monument ? 'true' : 'false'}</td>
                  <td>{kunstwerk.monumentnummer}</td>
                  <td>{kunstwerk.eobjectnaam}</td>
                  <td>{kunstwerk.eobjectnummer}</td>
                  <td>{kunstwerk.onderhoudsregime}</td>
                  <td>{kunstwerk.oppervlakte}</td>
                  <td>{kunstwerk.orientatie}</td>
                  <td>{kunstwerk.technischelevensduur}</td>
                  <td>{kunstwerk.typefundering}</td>
                  <td>{kunstwerk.typemonument}</td>
                  <td>{kunstwerk.vervangingswaarde}</td>
                  <td>{kunstwerk.wegnummer}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/kunstwerk/${kunstwerk.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/kunstwerk/${kunstwerk.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/kunstwerk/${kunstwerk.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Kunstwerks found</div>
        )}
      </div>
    </div>
  );
};

export default Kunstwerk;
