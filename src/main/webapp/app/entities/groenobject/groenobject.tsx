import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './groenobject.reducer';

export const Groenobject = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const groenobjectList = useAppSelector(state => state.groenobject.entities);
  const loading = useAppSelector(state => state.groenobject.loading);

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
      <h2 id="groenobject-heading" data-cy="GroenobjectHeading">
        Groenobjects
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/groenobject/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Groenobject
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {groenobjectList && groenobjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantalobstakels')}>
                  Aantalobstakels <FontAwesomeIcon icon={getSortIconByFieldName('aantalobstakels')} />
                </th>
                <th className="hand" onClick={sort('aantalzijden')}>
                  Aantalzijden <FontAwesomeIcon icon={getSortIconByFieldName('aantalzijden')} />
                </th>
                <th className="hand" onClick={sort('afvoeren')}>
                  Afvoeren <FontAwesomeIcon icon={getSortIconByFieldName('afvoeren')} />
                </th>
                <th className="hand" onClick={sort('bereikbaarheid')}>
                  Bereikbaarheid <FontAwesomeIcon icon={getSortIconByFieldName('bereikbaarheid')} />
                </th>
                <th className="hand" onClick={sort('bergendvermogen')}>
                  Bergendvermogen <FontAwesomeIcon icon={getSortIconByFieldName('bergendvermogen')} />
                </th>
                <th className="hand" onClick={sort('bewerkingspercentage')}>
                  Bewerkingspercentage <FontAwesomeIcon icon={getSortIconByFieldName('bewerkingspercentage')} />
                </th>
                <th className="hand" onClick={sort('bgtfysiekvoorkomen')}>
                  Bgtfysiekvoorkomen <FontAwesomeIcon icon={getSortIconByFieldName('bgtfysiekvoorkomen')} />
                </th>
                <th className="hand" onClick={sort('bollen')}>
                  Bollen <FontAwesomeIcon icon={getSortIconByFieldName('bollen')} />
                </th>
                <th className="hand" onClick={sort('breedte')}>
                  Breedte <FontAwesomeIcon icon={getSortIconByFieldName('breedte')} />
                </th>
                <th className="hand" onClick={sort('breedteklassehaag')}>
                  Breedteklassehaag <FontAwesomeIcon icon={getSortIconByFieldName('breedteklassehaag')} />
                </th>
                <th className="hand" onClick={sort('bvc')}>
                  Bvc <FontAwesomeIcon icon={getSortIconByFieldName('bvc')} />
                </th>
                <th className="hand" onClick={sort('cultuurhistorischwaardevol')}>
                  Cultuurhistorischwaardevol <FontAwesomeIcon icon={getSortIconByFieldName('cultuurhistorischwaardevol')} />
                </th>
                <th className="hand" onClick={sort('draagkrachtig')}>
                  Draagkrachtig <FontAwesomeIcon icon={getSortIconByFieldName('draagkrachtig')} />
                </th>
                <th className="hand" onClick={sort('ecologischbeheer')}>
                  Ecologischbeheer <FontAwesomeIcon icon={getSortIconByFieldName('ecologischbeheer')} />
                </th>
                <th className="hand" onClick={sort('fysiekvoorkomenimgeo')}>
                  Fysiekvoorkomenimgeo <FontAwesomeIcon icon={getSortIconByFieldName('fysiekvoorkomenimgeo')} />
                </th>
                <th className="hand" onClick={sort('gewenstsluitingspercentage')}>
                  Gewenstsluitingspercentage <FontAwesomeIcon icon={getSortIconByFieldName('gewenstsluitingspercentage')} />
                </th>
                <th className="hand" onClick={sort('groenobjectbereikbaarheidplus')}>
                  Groenobjectbereikbaarheidplus <FontAwesomeIcon icon={getSortIconByFieldName('groenobjectbereikbaarheidplus')} />
                </th>
                <th className="hand" onClick={sort('groenobjectconstructielaag')}>
                  Groenobjectconstructielaag <FontAwesomeIcon icon={getSortIconByFieldName('groenobjectconstructielaag')} />
                </th>
                <th className="hand" onClick={sort('groenobjectrand')}>
                  Groenobjectrand <FontAwesomeIcon icon={getSortIconByFieldName('groenobjectrand')} />
                </th>
                <th className="hand" onClick={sort('groenobjectsoortnaam')}>
                  Groenobjectsoortnaam <FontAwesomeIcon icon={getSortIconByFieldName('groenobjectsoortnaam')} />
                </th>
                <th className="hand" onClick={sort('haagvoetlengte')}>
                  Haagvoetlengte <FontAwesomeIcon icon={getSortIconByFieldName('haagvoetlengte')} />
                </th>
                <th className="hand" onClick={sort('haagvoetoppervlakte')}>
                  Haagvoetoppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('haagvoetoppervlakte')} />
                </th>
                <th className="hand" onClick={sort('herplantplicht')}>
                  Herplantplicht <FontAwesomeIcon icon={getSortIconByFieldName('herplantplicht')} />
                </th>
                <th className="hand" onClick={sort('hoogte')}>
                  Hoogte <FontAwesomeIcon icon={getSortIconByFieldName('hoogte')} />
                </th>
                <th className="hand" onClick={sort('hoogteklassehaag')}>
                  Hoogteklassehaag <FontAwesomeIcon icon={getSortIconByFieldName('hoogteklassehaag')} />
                </th>
                <th className="hand" onClick={sort('knipfrequentie')}>
                  Knipfrequentie <FontAwesomeIcon icon={getSortIconByFieldName('knipfrequentie')} />
                </th>
                <th className="hand" onClick={sort('knipoppervlakte')}>
                  Knipoppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('knipoppervlakte')} />
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
                <th className="hand" onClick={sort('maaifrequentie')}>
                  Maaifrequentie <FontAwesomeIcon icon={getSortIconByFieldName('maaifrequentie')} />
                </th>
                <th className="hand" onClick={sort('maximalevalhoogte')}>
                  Maximalevalhoogte <FontAwesomeIcon icon={getSortIconByFieldName('maximalevalhoogte')} />
                </th>
                <th className="hand" onClick={sort('eobjectnummer')}>
                  Eobjectnummer <FontAwesomeIcon icon={getSortIconByFieldName('eobjectnummer')} />
                </th>
                <th className="hand" onClick={sort('obstakels')}>
                  Obstakels <FontAwesomeIcon icon={getSortIconByFieldName('obstakels')} />
                </th>
                <th className="hand" onClick={sort('omtrek')}>
                  Omtrek <FontAwesomeIcon icon={getSortIconByFieldName('omtrek')} />
                </th>
                <th className="hand" onClick={sort('ondergroei')}>
                  Ondergroei <FontAwesomeIcon icon={getSortIconByFieldName('ondergroei')} />
                </th>
                <th className="hand" onClick={sort('oppervlakte')}>
                  Oppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('oppervlakte')} />
                </th>
                <th className="hand" onClick={sort('optalud')}>
                  Optalud <FontAwesomeIcon icon={getSortIconByFieldName('optalud')} />
                </th>
                <th className="hand" onClick={sort('taludsteilte')}>
                  Taludsteilte <FontAwesomeIcon icon={getSortIconByFieldName('taludsteilte')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('typebewerking')}>
                  Typebewerking <FontAwesomeIcon icon={getSortIconByFieldName('typebewerking')} />
                </th>
                <th className="hand" onClick={sort('typeomgevingsrisicoklasse')}>
                  Typeomgevingsrisicoklasse <FontAwesomeIcon icon={getSortIconByFieldName('typeomgevingsrisicoklasse')} />
                </th>
                <th className="hand" onClick={sort('typeplus')}>
                  Typeplus <FontAwesomeIcon icon={getSortIconByFieldName('typeplus')} />
                </th>
                <th className="hand" onClick={sort('typeplus2')}>
                  Typeplus 2 <FontAwesomeIcon icon={getSortIconByFieldName('typeplus2')} />
                </th>
                <th className="hand" onClick={sort('veiligheidsklasseboom')}>
                  Veiligheidsklasseboom <FontAwesomeIcon icon={getSortIconByFieldName('veiligheidsklasseboom')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {groenobjectList.map((groenobject, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/groenobject/${groenobject.id}`} color="link" size="sm">
                      {groenobject.id}
                    </Button>
                  </td>
                  <td>{groenobject.aantalobstakels}</td>
                  <td>{groenobject.aantalzijden}</td>
                  <td>{groenobject.afvoeren ? 'true' : 'false'}</td>
                  <td>{groenobject.bereikbaarheid}</td>
                  <td>{groenobject.bergendvermogen}</td>
                  <td>{groenobject.bewerkingspercentage}</td>
                  <td>{groenobject.bgtfysiekvoorkomen}</td>
                  <td>{groenobject.bollen ? 'true' : 'false'}</td>
                  <td>{groenobject.breedte}</td>
                  <td>{groenobject.breedteklassehaag}</td>
                  <td>{groenobject.bvc ? 'true' : 'false'}</td>
                  <td>{groenobject.cultuurhistorischwaardevol}</td>
                  <td>{groenobject.draagkrachtig ? 'true' : 'false'}</td>
                  <td>{groenobject.ecologischbeheer ? 'true' : 'false'}</td>
                  <td>{groenobject.fysiekvoorkomenimgeo}</td>
                  <td>{groenobject.gewenstsluitingspercentage}</td>
                  <td>{groenobject.groenobjectbereikbaarheidplus}</td>
                  <td>{groenobject.groenobjectconstructielaag}</td>
                  <td>{groenobject.groenobjectrand}</td>
                  <td>{groenobject.groenobjectsoortnaam}</td>
                  <td>{groenobject.haagvoetlengte}</td>
                  <td>{groenobject.haagvoetoppervlakte}</td>
                  <td>{groenobject.herplantplicht ? 'true' : 'false'}</td>
                  <td>{groenobject.hoogte}</td>
                  <td>{groenobject.hoogteklassehaag}</td>
                  <td>{groenobject.knipfrequentie}</td>
                  <td>{groenobject.knipoppervlakte}</td>
                  <td>{groenobject.kwaliteitsniveauactueel}</td>
                  <td>{groenobject.kwaliteitsniveaugewenst}</td>
                  <td>{groenobject.lengte}</td>
                  <td>{groenobject.leverancier}</td>
                  <td>{groenobject.maaifrequentie}</td>
                  <td>{groenobject.maximalevalhoogte}</td>
                  <td>{groenobject.eobjectnummer}</td>
                  <td>{groenobject.obstakels ? 'true' : 'false'}</td>
                  <td>{groenobject.omtrek}</td>
                  <td>{groenobject.ondergroei}</td>
                  <td>{groenobject.oppervlakte}</td>
                  <td>{groenobject.optalud}</td>
                  <td>{groenobject.taludsteilte}</td>
                  <td>{groenobject.type}</td>
                  <td>{groenobject.typebewerking}</td>
                  <td>{groenobject.typeomgevingsrisicoklasse}</td>
                  <td>{groenobject.typeplus}</td>
                  <td>{groenobject.typeplus2}</td>
                  <td>{groenobject.veiligheidsklasseboom}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/groenobject/${groenobject.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/groenobject/${groenobject.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/groenobject/${groenobject.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Groenobjects found</div>
        )}
      </div>
    </div>
  );
};

export default Groenobject;
