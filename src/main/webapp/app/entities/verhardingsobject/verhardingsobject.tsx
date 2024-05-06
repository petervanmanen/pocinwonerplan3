import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './verhardingsobject.reducer';

export const Verhardingsobject = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const verhardingsobjectList = useAppSelector(state => state.verhardingsobject.entities);
  const loading = useAppSelector(state => state.verhardingsobject.loading);

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
      <h2 id="verhardingsobject-heading" data-cy="VerhardingsobjectHeading">
        Verhardingsobjects
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/verhardingsobject/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Verhardingsobject
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {verhardingsobjectList && verhardingsobjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanleghoogte')}>
                  Aanleghoogte <FontAwesomeIcon icon={getSortIconByFieldName('aanleghoogte')} />
                </th>
                <th className="hand" onClick={sort('aanofvrijliggend')}>
                  Aanofvrijliggend <FontAwesomeIcon icon={getSortIconByFieldName('aanofvrijliggend')} />
                </th>
                <th className="hand" onClick={sort('aantaldeklagen')}>
                  Aantaldeklagen <FontAwesomeIcon icon={getSortIconByFieldName('aantaldeklagen')} />
                </th>
                <th className="hand" onClick={sort('aantalonderlagen')}>
                  Aantalonderlagen <FontAwesomeIcon icon={getSortIconByFieldName('aantalonderlagen')} />
                </th>
                <th className="hand" onClick={sort('aantaltussenlagen')}>
                  Aantaltussenlagen <FontAwesomeIcon icon={getSortIconByFieldName('aantaltussenlagen')} />
                </th>
                <th className="hand" onClick={sort('afmeting')}>
                  Afmeting <FontAwesomeIcon icon={getSortIconByFieldName('afmeting')} />
                </th>
                <th className="hand" onClick={sort('belasting')}>
                  Belasting <FontAwesomeIcon icon={getSortIconByFieldName('belasting')} />
                </th>
                <th className="hand" onClick={sort('bergendvermogen')}>
                  Bergendvermogen <FontAwesomeIcon icon={getSortIconByFieldName('bergendvermogen')} />
                </th>
                <th className="hand" onClick={sort('bgtfysiekvoorkomen')}>
                  Bgtfysiekvoorkomen <FontAwesomeIcon icon={getSortIconByFieldName('bgtfysiekvoorkomen')} />
                </th>
                <th className="hand" onClick={sort('breedte')}>
                  Breedte <FontAwesomeIcon icon={getSortIconByFieldName('breedte')} />
                </th>
                <th className="hand" onClick={sort('dikteconstructie')}>
                  Dikteconstructie <FontAwesomeIcon icon={getSortIconByFieldName('dikteconstructie')} />
                </th>
                <th className="hand" onClick={sort('draagkrachtig')}>
                  Draagkrachtig <FontAwesomeIcon icon={getSortIconByFieldName('draagkrachtig')} />
                </th>
                <th className="hand" onClick={sort('formaat')}>
                  Formaat <FontAwesomeIcon icon={getSortIconByFieldName('formaat')} />
                </th>
                <th className="hand" onClick={sort('fysiekvoorkomenimgeo')}>
                  Fysiekvoorkomenimgeo <FontAwesomeIcon icon={getSortIconByFieldName('fysiekvoorkomenimgeo')} />
                </th>
                <th className="hand" onClick={sort('geluidsreducerend')}>
                  Geluidsreducerend <FontAwesomeIcon icon={getSortIconByFieldName('geluidsreducerend')} />
                </th>
                <th className="hand" onClick={sort('jaarconserveren')}>
                  Jaarconserveren <FontAwesomeIcon icon={getSortIconByFieldName('jaarconserveren')} />
                </th>
                <th className="hand" onClick={sort('jaaronderhouduitgevoerd')}>
                  Jaaronderhouduitgevoerd <FontAwesomeIcon icon={getSortIconByFieldName('jaaronderhouduitgevoerd')} />
                </th>
                <th className="hand" onClick={sort('jaarpraktischeinde')}>
                  Jaarpraktischeinde <FontAwesomeIcon icon={getSortIconByFieldName('jaarpraktischeinde')} />
                </th>
                <th className="hand" onClick={sort('kleur')}>
                  Kleur <FontAwesomeIcon icon={getSortIconByFieldName('kleur')} />
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
                <th className="hand" onClick={sort('lengtekunstgras')}>
                  Lengtekunstgras <FontAwesomeIcon icon={getSortIconByFieldName('lengtekunstgras')} />
                </th>
                <th className="hand" onClick={sort('lengtevoegen')}>
                  Lengtevoegen <FontAwesomeIcon icon={getSortIconByFieldName('lengtevoegen')} />
                </th>
                <th className="hand" onClick={sort('levensduur')}>
                  Levensduur <FontAwesomeIcon icon={getSortIconByFieldName('levensduur')} />
                </th>
                <th className="hand" onClick={sort('materiaal')}>
                  Materiaal <FontAwesomeIcon icon={getSortIconByFieldName('materiaal')} />
                </th>
                <th className="hand" onClick={sort('maximalevalhoogte')}>
                  Maximalevalhoogte <FontAwesomeIcon icon={getSortIconByFieldName('maximalevalhoogte')} />
                </th>
                <th className="hand" onClick={sort('omtrek')}>
                  Omtrek <FontAwesomeIcon icon={getSortIconByFieldName('omtrek')} />
                </th>
                <th className="hand" onClick={sort('ondergrondcode')}>
                  Ondergrondcode <FontAwesomeIcon icon={getSortIconByFieldName('ondergrondcode')} />
                </th>
                <th className="hand" onClick={sort('oppervlakte')}>
                  Oppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('oppervlakte')} />
                </th>
                <th className="hand" onClick={sort('optalud')}>
                  Optalud <FontAwesomeIcon icon={getSortIconByFieldName('optalud')} />
                </th>
                <th className="hand" onClick={sort('plaatsorientatie')}>
                  Plaatsorientatie <FontAwesomeIcon icon={getSortIconByFieldName('plaatsorientatie')} />
                </th>
                <th className="hand" onClick={sort('prijsaanschaf')}>
                  Prijsaanschaf <FontAwesomeIcon icon={getSortIconByFieldName('prijsaanschaf')} />
                </th>
                <th className="hand" onClick={sort('rijstrook')}>
                  Rijstrook <FontAwesomeIcon icon={getSortIconByFieldName('rijstrook')} />
                </th>
                <th className="hand" onClick={sort('soortvoeg')}>
                  Soortvoeg <FontAwesomeIcon icon={getSortIconByFieldName('soortvoeg')} />
                </th>
                <th className="hand" onClick={sort('toelichtinggemengdebestrating')}>
                  Toelichtinggemengdebestrating <FontAwesomeIcon icon={getSortIconByFieldName('toelichtinggemengdebestrating')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('typeconstructie')}>
                  Typeconstructie <FontAwesomeIcon icon={getSortIconByFieldName('typeconstructie')} />
                </th>
                <th className="hand" onClick={sort('typefundering')}>
                  Typefundering <FontAwesomeIcon icon={getSortIconByFieldName('typefundering')} />
                </th>
                <th className="hand" onClick={sort('typeplus')}>
                  Typeplus <FontAwesomeIcon icon={getSortIconByFieldName('typeplus')} />
                </th>
                <th className="hand" onClick={sort('typeplus2')}>
                  Typeplus 2 <FontAwesomeIcon icon={getSortIconByFieldName('typeplus2')} />
                </th>
                <th className="hand" onClick={sort('typerijstrook')}>
                  Typerijstrook <FontAwesomeIcon icon={getSortIconByFieldName('typerijstrook')} />
                </th>
                <th className="hand" onClick={sort('typevoeg')}>
                  Typevoeg <FontAwesomeIcon icon={getSortIconByFieldName('typevoeg')} />
                </th>
                <th className="hand" onClick={sort('typevoegvulling')}>
                  Typevoegvulling <FontAwesomeIcon icon={getSortIconByFieldName('typevoegvulling')} />
                </th>
                <th className="hand" onClick={sort('vegen')}>
                  Vegen <FontAwesomeIcon icon={getSortIconByFieldName('vegen')} />
                </th>
                <th className="hand" onClick={sort('verhardingsobjectconstructielaag')}>
                  Verhardingsobjectconstructielaag <FontAwesomeIcon icon={getSortIconByFieldName('verhardingsobjectconstructielaag')} />
                </th>
                <th className="hand" onClick={sort('verhardingsobjectmodaliteit')}>
                  Verhardingsobjectmodaliteit <FontAwesomeIcon icon={getSortIconByFieldName('verhardingsobjectmodaliteit')} />
                </th>
                <th className="hand" onClick={sort('verhardingsobjectrand')}>
                  Verhardingsobjectrand <FontAwesomeIcon icon={getSortIconByFieldName('verhardingsobjectrand')} />
                </th>
                <th className="hand" onClick={sort('verhardingsobjectwegfunctie')}>
                  Verhardingsobjectwegfunctie <FontAwesomeIcon icon={getSortIconByFieldName('verhardingsobjectwegfunctie')} />
                </th>
                <th className="hand" onClick={sort('verhoogdeligging')}>
                  Verhoogdeligging <FontAwesomeIcon icon={getSortIconByFieldName('verhoogdeligging')} />
                </th>
                <th className="hand" onClick={sort('vulmateriaalkunstgras')}>
                  Vulmateriaalkunstgras <FontAwesomeIcon icon={getSortIconByFieldName('vulmateriaalkunstgras')} />
                </th>
                <th className="hand" onClick={sort('waterdoorlatendheid')}>
                  Waterdoorlatendheid <FontAwesomeIcon icon={getSortIconByFieldName('waterdoorlatendheid')} />
                </th>
                <th className="hand" onClick={sort('wegas')}>
                  Wegas <FontAwesomeIcon icon={getSortIconByFieldName('wegas')} />
                </th>
                <th className="hand" onClick={sort('wegcategoriedv')}>
                  Wegcategoriedv <FontAwesomeIcon icon={getSortIconByFieldName('wegcategoriedv')} />
                </th>
                <th className="hand" onClick={sort('wegcategoriedvplus')}>
                  Wegcategoriedvplus <FontAwesomeIcon icon={getSortIconByFieldName('wegcategoriedvplus')} />
                </th>
                <th className="hand" onClick={sort('wegnummer')}>
                  Wegnummer <FontAwesomeIcon icon={getSortIconByFieldName('wegnummer')} />
                </th>
                <th className="hand" onClick={sort('wegtypebestaand')}>
                  Wegtypebestaand <FontAwesomeIcon icon={getSortIconByFieldName('wegtypebestaand')} />
                </th>
                <th className="hand" onClick={sort('wegvak')}>
                  Wegvak <FontAwesomeIcon icon={getSortIconByFieldName('wegvak')} />
                </th>
                <th className="hand" onClick={sort('wegvaknummer')}>
                  Wegvaknummer <FontAwesomeIcon icon={getSortIconByFieldName('wegvaknummer')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {verhardingsobjectList.map((verhardingsobject, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/verhardingsobject/${verhardingsobject.id}`} color="link" size="sm">
                      {verhardingsobject.id}
                    </Button>
                  </td>
                  <td>{verhardingsobject.aanleghoogte}</td>
                  <td>{verhardingsobject.aanofvrijliggend}</td>
                  <td>{verhardingsobject.aantaldeklagen}</td>
                  <td>{verhardingsobject.aantalonderlagen}</td>
                  <td>{verhardingsobject.aantaltussenlagen}</td>
                  <td>{verhardingsobject.afmeting}</td>
                  <td>{verhardingsobject.belasting}</td>
                  <td>{verhardingsobject.bergendvermogen}</td>
                  <td>{verhardingsobject.bgtfysiekvoorkomen}</td>
                  <td>{verhardingsobject.breedte}</td>
                  <td>{verhardingsobject.dikteconstructie}</td>
                  <td>{verhardingsobject.draagkrachtig ? 'true' : 'false'}</td>
                  <td>{verhardingsobject.formaat}</td>
                  <td>{verhardingsobject.fysiekvoorkomenimgeo}</td>
                  <td>{verhardingsobject.geluidsreducerend ? 'true' : 'false'}</td>
                  <td>{verhardingsobject.jaarconserveren}</td>
                  <td>{verhardingsobject.jaaronderhouduitgevoerd}</td>
                  <td>{verhardingsobject.jaarpraktischeinde}</td>
                  <td>{verhardingsobject.kleur}</td>
                  <td>{verhardingsobject.kwaliteitsniveauactueel}</td>
                  <td>{verhardingsobject.kwaliteitsniveaugewenst}</td>
                  <td>{verhardingsobject.lengte}</td>
                  <td>{verhardingsobject.lengtekunstgras}</td>
                  <td>{verhardingsobject.lengtevoegen}</td>
                  <td>{verhardingsobject.levensduur}</td>
                  <td>{verhardingsobject.materiaal}</td>
                  <td>{verhardingsobject.maximalevalhoogte}</td>
                  <td>{verhardingsobject.omtrek}</td>
                  <td>{verhardingsobject.ondergrondcode}</td>
                  <td>{verhardingsobject.oppervlakte}</td>
                  <td>{verhardingsobject.optalud}</td>
                  <td>{verhardingsobject.plaatsorientatie}</td>
                  <td>{verhardingsobject.prijsaanschaf}</td>
                  <td>{verhardingsobject.rijstrook}</td>
                  <td>{verhardingsobject.soortvoeg}</td>
                  <td>{verhardingsobject.toelichtinggemengdebestrating}</td>
                  <td>{verhardingsobject.type}</td>
                  <td>{verhardingsobject.typeconstructie}</td>
                  <td>{verhardingsobject.typefundering}</td>
                  <td>{verhardingsobject.typeplus}</td>
                  <td>{verhardingsobject.typeplus2}</td>
                  <td>{verhardingsobject.typerijstrook}</td>
                  <td>{verhardingsobject.typevoeg}</td>
                  <td>{verhardingsobject.typevoegvulling}</td>
                  <td>{verhardingsobject.vegen}</td>
                  <td>{verhardingsobject.verhardingsobjectconstructielaag}</td>
                  <td>{verhardingsobject.verhardingsobjectmodaliteit}</td>
                  <td>{verhardingsobject.verhardingsobjectrand}</td>
                  <td>{verhardingsobject.verhardingsobjectwegfunctie}</td>
                  <td>{verhardingsobject.verhoogdeligging ? 'true' : 'false'}</td>
                  <td>{verhardingsobject.vulmateriaalkunstgras}</td>
                  <td>{verhardingsobject.waterdoorlatendheid}</td>
                  <td>{verhardingsobject.wegas}</td>
                  <td>{verhardingsobject.wegcategoriedv}</td>
                  <td>{verhardingsobject.wegcategoriedvplus}</td>
                  <td>{verhardingsobject.wegnummer}</td>
                  <td>{verhardingsobject.wegtypebestaand}</td>
                  <td>{verhardingsobject.wegvak}</td>
                  <td>{verhardingsobject.wegvaknummer}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/verhardingsobject/${verhardingsobject.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/verhardingsobject/${verhardingsobject.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/verhardingsobject/${verhardingsobject.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Verhardingsobjects found</div>
        )}
      </div>
    </div>
  );
};

export default Verhardingsobject;
