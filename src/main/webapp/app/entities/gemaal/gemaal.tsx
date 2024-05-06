import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './gemaal.reducer';

export const Gemaal = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const gemaalList = useAppSelector(state => state.gemaal.entities);
  const loading = useAppSelector(state => state.gemaal.loading);

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
      <h2 id="gemaal-heading" data-cy="GemaalHeading">
        Gemaals
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/gemaal/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Gemaal
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {gemaalList && gemaalList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantalbedrijfsaansluitingen')}>
                  Aantalbedrijfsaansluitingen <FontAwesomeIcon icon={getSortIconByFieldName('aantalbedrijfsaansluitingen')} />
                </th>
                <th className="hand" onClick={sort('aantalhuisaansluitingen')}>
                  Aantalhuisaansluitingen <FontAwesomeIcon icon={getSortIconByFieldName('aantalhuisaansluitingen')} />
                </th>
                <th className="hand" onClick={sort('aantalpompen')}>
                  Aantalpompen <FontAwesomeIcon icon={getSortIconByFieldName('aantalpompen')} />
                </th>
                <th className="hand" onClick={sort('bedienaar')}>
                  Bedienaar <FontAwesomeIcon icon={getSortIconByFieldName('bedienaar')} />
                </th>
                <th className="hand" onClick={sort('effectievegemaalcapaciteit')}>
                  Effectievegemaalcapaciteit <FontAwesomeIcon icon={getSortIconByFieldName('effectievegemaalcapaciteit')} />
                </th>
                <th className="hand" onClick={sort('hijsinrichting')}>
                  Hijsinrichting <FontAwesomeIcon icon={getSortIconByFieldName('hijsinrichting')} />
                </th>
                <th className="hand" onClick={sort('lanceerinrichting')}>
                  Lanceerinrichting <FontAwesomeIcon icon={getSortIconByFieldName('lanceerinrichting')} />
                </th>
                <th className="hand" onClick={sort('pompeninsamenloop')}>
                  Pompeninsamenloop <FontAwesomeIcon icon={getSortIconByFieldName('pompeninsamenloop')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('veiligheidsrooster')}>
                  Veiligheidsrooster <FontAwesomeIcon icon={getSortIconByFieldName('veiligheidsrooster')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {gemaalList.map((gemaal, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/gemaal/${gemaal.id}`} color="link" size="sm">
                      {gemaal.id}
                    </Button>
                  </td>
                  <td>{gemaal.aantalbedrijfsaansluitingen}</td>
                  <td>{gemaal.aantalhuisaansluitingen}</td>
                  <td>{gemaal.aantalpompen}</td>
                  <td>{gemaal.bedienaar}</td>
                  <td>{gemaal.effectievegemaalcapaciteit}</td>
                  <td>{gemaal.hijsinrichting ? 'true' : 'false'}</td>
                  <td>{gemaal.lanceerinrichting ? 'true' : 'false'}</td>
                  <td>{gemaal.pompeninsamenloop ? 'true' : 'false'}</td>
                  <td>{gemaal.type}</td>
                  <td>{gemaal.veiligheidsrooster ? 'true' : 'false'}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/gemaal/${gemaal.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/gemaal/${gemaal.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/gemaal/${gemaal.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Gemaals found</div>
        )}
      </div>
    </div>
  );
};

export default Gemaal;
