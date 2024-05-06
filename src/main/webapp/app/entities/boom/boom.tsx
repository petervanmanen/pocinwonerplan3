import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './boom.reducer';

export const Boom = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const boomList = useAppSelector(state => state.boom.entities);
  const loading = useAppSelector(state => state.boom.loading);

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
      <h2 id="boom-heading" data-cy="BoomHeading">
        Booms
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/boom/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Boom
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {boomList && boomList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('beleidsstatus')}>
                  Beleidsstatus <FontAwesomeIcon icon={getSortIconByFieldName('beleidsstatus')} />
                </th>
                <th className="hand" onClick={sort('beoogdeomlooptijd')}>
                  Beoogdeomlooptijd <FontAwesomeIcon icon={getSortIconByFieldName('beoogdeomlooptijd')} />
                </th>
                <th className="hand" onClick={sort('boombeeld')}>
                  Boombeeld <FontAwesomeIcon icon={getSortIconByFieldName('boombeeld')} />
                </th>
                <th className="hand" onClick={sort('boombeschermer')}>
                  Boombeschermer <FontAwesomeIcon icon={getSortIconByFieldName('boombeschermer')} />
                </th>
                <th className="hand" onClick={sort('boomgroep')}>
                  Boomgroep <FontAwesomeIcon icon={getSortIconByFieldName('boomgroep')} />
                </th>
                <th className="hand" onClick={sort('boomhoogteactueel')}>
                  Boomhoogteactueel <FontAwesomeIcon icon={getSortIconByFieldName('boomhoogteactueel')} />
                </th>
                <th className="hand" onClick={sort('boomhoogteklasseactueel')}>
                  Boomhoogteklasseactueel <FontAwesomeIcon icon={getSortIconByFieldName('boomhoogteklasseactueel')} />
                </th>
                <th className="hand" onClick={sort('boomhoogteklasseeindebeeld')}>
                  Boomhoogteklasseeindebeeld <FontAwesomeIcon icon={getSortIconByFieldName('boomhoogteklasseeindebeeld')} />
                </th>
                <th className="hand" onClick={sort('boomspiegel')}>
                  Boomspiegel <FontAwesomeIcon icon={getSortIconByFieldName('boomspiegel')} />
                </th>
                <th className="hand" onClick={sort('boomtypebeschermingsstatusplus')}>
                  Boomtypebeschermingsstatusplus <FontAwesomeIcon icon={getSortIconByFieldName('boomtypebeschermingsstatusplus')} />
                </th>
                <th className="hand" onClick={sort('boomvoorziening')}>
                  Boomvoorziening <FontAwesomeIcon icon={getSortIconByFieldName('boomvoorziening')} />
                </th>
                <th className="hand" onClick={sort('controlefrequentie')}>
                  Controlefrequentie <FontAwesomeIcon icon={getSortIconByFieldName('controlefrequentie')} />
                </th>
                <th className="hand" onClick={sort('feestverlichting')}>
                  Feestverlichting <FontAwesomeIcon icon={getSortIconByFieldName('feestverlichting')} />
                </th>
                <th className="hand" onClick={sort('groeifase')}>
                  Groeifase <FontAwesomeIcon icon={getSortIconByFieldName('groeifase')} />
                </th>
                <th className="hand" onClick={sort('groeiplaatsinrichting')}>
                  Groeiplaatsinrichting <FontAwesomeIcon icon={getSortIconByFieldName('groeiplaatsinrichting')} />
                </th>
                <th className="hand" onClick={sort('herplantplicht')}>
                  Herplantplicht <FontAwesomeIcon icon={getSortIconByFieldName('herplantplicht')} />
                </th>
                <th className="hand" onClick={sort('kiemjaar')}>
                  Kiemjaar <FontAwesomeIcon icon={getSortIconByFieldName('kiemjaar')} />
                </th>
                <th className="hand" onClick={sort('kroondiameterklasseactueel')}>
                  Kroondiameterklasseactueel <FontAwesomeIcon icon={getSortIconByFieldName('kroondiameterklasseactueel')} />
                </th>
                <th className="hand" onClick={sort('kroondiameterklasseeindebeeld')}>
                  Kroondiameterklasseeindebeeld <FontAwesomeIcon icon={getSortIconByFieldName('kroondiameterklasseeindebeeld')} />
                </th>
                <th className="hand" onClick={sort('kroonvolume')}>
                  Kroonvolume <FontAwesomeIcon icon={getSortIconByFieldName('kroonvolume')} />
                </th>
                <th className="hand" onClick={sort('leeftijd')}>
                  Leeftijd <FontAwesomeIcon icon={getSortIconByFieldName('leeftijd')} />
                </th>
                <th className="hand" onClick={sort('meerstammig')}>
                  Meerstammig <FontAwesomeIcon icon={getSortIconByFieldName('meerstammig')} />
                </th>
                <th className="hand" onClick={sort('monetaireboomwaarde')}>
                  Monetaireboomwaarde <FontAwesomeIcon icon={getSortIconByFieldName('monetaireboomwaarde')} />
                </th>
                <th className="hand" onClick={sort('snoeifase')}>
                  Snoeifase <FontAwesomeIcon icon={getSortIconByFieldName('snoeifase')} />
                </th>
                <th className="hand" onClick={sort('stamdiameter')}>
                  Stamdiameter <FontAwesomeIcon icon={getSortIconByFieldName('stamdiameter')} />
                </th>
                <th className="hand" onClick={sort('stamdiameterklasse')}>
                  Stamdiameterklasse <FontAwesomeIcon icon={getSortIconByFieldName('stamdiameterklasse')} />
                </th>
                <th className="hand" onClick={sort('takvrijeruimtetotgebouw')}>
                  Takvrijeruimtetotgebouw <FontAwesomeIcon icon={getSortIconByFieldName('takvrijeruimtetotgebouw')} />
                </th>
                <th className="hand" onClick={sort('takvrijestam')}>
                  Takvrijestam <FontAwesomeIcon icon={getSortIconByFieldName('takvrijestam')} />
                </th>
                <th className="hand" onClick={sort('takvrijezoneprimair')}>
                  Takvrijezoneprimair <FontAwesomeIcon icon={getSortIconByFieldName('takvrijezoneprimair')} />
                </th>
                <th className="hand" onClick={sort('takvrijezonesecundair')}>
                  Takvrijezonesecundair <FontAwesomeIcon icon={getSortIconByFieldName('takvrijezonesecundair')} />
                </th>
                <th className="hand" onClick={sort('transponder')}>
                  Transponder <FontAwesomeIcon icon={getSortIconByFieldName('transponder')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('typebeschermingsstatus')}>
                  Typebeschermingsstatus <FontAwesomeIcon icon={getSortIconByFieldName('typebeschermingsstatus')} />
                </th>
                <th className="hand" onClick={sort('typeomgevingsrisicoklasse')}>
                  Typeomgevingsrisicoklasse <FontAwesomeIcon icon={getSortIconByFieldName('typeomgevingsrisicoklasse')} />
                </th>
                <th className="hand" onClick={sort('typeplus')}>
                  Typeplus <FontAwesomeIcon icon={getSortIconByFieldName('typeplus')} />
                </th>
                <th className="hand" onClick={sort('typevermeerderingsvorm')}>
                  Typevermeerderingsvorm <FontAwesomeIcon icon={getSortIconByFieldName('typevermeerderingsvorm')} />
                </th>
                <th className="hand" onClick={sort('veiligheidsklasseboom')}>
                  Veiligheidsklasseboom <FontAwesomeIcon icon={getSortIconByFieldName('veiligheidsklasseboom')} />
                </th>
                <th className="hand" onClick={sort('verplant')}>
                  Verplant <FontAwesomeIcon icon={getSortIconByFieldName('verplant')} />
                </th>
                <th className="hand" onClick={sort('verplantbaar')}>
                  Verplantbaar <FontAwesomeIcon icon={getSortIconByFieldName('verplantbaar')} />
                </th>
                <th className="hand" onClick={sort('vrijedoorrijhoogte')}>
                  Vrijedoorrijhoogte <FontAwesomeIcon icon={getSortIconByFieldName('vrijedoorrijhoogte')} />
                </th>
                <th className="hand" onClick={sort('vrijedoorrijhoogteprimair')}>
                  Vrijedoorrijhoogteprimair <FontAwesomeIcon icon={getSortIconByFieldName('vrijedoorrijhoogteprimair')} />
                </th>
                <th className="hand" onClick={sort('vrijedoorrijhoogtesecundair')}>
                  Vrijedoorrijhoogtesecundair <FontAwesomeIcon icon={getSortIconByFieldName('vrijedoorrijhoogtesecundair')} />
                </th>
                <th className="hand" onClick={sort('vrijetakval')}>
                  Vrijetakval <FontAwesomeIcon icon={getSortIconByFieldName('vrijetakval')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {boomList.map((boom, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/boom/${boom.id}`} color="link" size="sm">
                      {boom.id}
                    </Button>
                  </td>
                  <td>{boom.beleidsstatus}</td>
                  <td>{boom.beoogdeomlooptijd}</td>
                  <td>{boom.boombeeld}</td>
                  <td>{boom.boombeschermer}</td>
                  <td>{boom.boomgroep}</td>
                  <td>{boom.boomhoogteactueel}</td>
                  <td>{boom.boomhoogteklasseactueel}</td>
                  <td>{boom.boomhoogteklasseeindebeeld}</td>
                  <td>{boom.boomspiegel}</td>
                  <td>{boom.boomtypebeschermingsstatusplus}</td>
                  <td>{boom.boomvoorziening}</td>
                  <td>{boom.controlefrequentie}</td>
                  <td>{boom.feestverlichting}</td>
                  <td>{boom.groeifase}</td>
                  <td>{boom.groeiplaatsinrichting}</td>
                  <td>{boom.herplantplicht ? 'true' : 'false'}</td>
                  <td>{boom.kiemjaar}</td>
                  <td>{boom.kroondiameterklasseactueel}</td>
                  <td>{boom.kroondiameterklasseeindebeeld}</td>
                  <td>{boom.kroonvolume}</td>
                  <td>{boom.leeftijd}</td>
                  <td>{boom.meerstammig ? 'true' : 'false'}</td>
                  <td>{boom.monetaireboomwaarde}</td>
                  <td>{boom.snoeifase}</td>
                  <td>{boom.stamdiameter}</td>
                  <td>{boom.stamdiameterklasse}</td>
                  <td>{boom.takvrijeruimtetotgebouw}</td>
                  <td>{boom.takvrijestam}</td>
                  <td>{boom.takvrijezoneprimair}</td>
                  <td>{boom.takvrijezonesecundair}</td>
                  <td>{boom.transponder}</td>
                  <td>{boom.type}</td>
                  <td>{boom.typebeschermingsstatus}</td>
                  <td>{boom.typeomgevingsrisicoklasse}</td>
                  <td>{boom.typeplus}</td>
                  <td>{boom.typevermeerderingsvorm}</td>
                  <td>{boom.veiligheidsklasseboom}</td>
                  <td>{boom.verplant ? 'true' : 'false'}</td>
                  <td>{boom.verplantbaar ? 'true' : 'false'}</td>
                  <td>{boom.vrijedoorrijhoogte}</td>
                  <td>{boom.vrijedoorrijhoogteprimair}</td>
                  <td>{boom.vrijedoorrijhoogtesecundair}</td>
                  <td>{boom.vrijetakval}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/boom/${boom.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/boom/${boom.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/boom/${boom.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Booms found</div>
        )}
      </div>
    </div>
  );
};

export default Boom;
