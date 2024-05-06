import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './overbruggingsobject.reducer';

export const OverbruggingsobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const overbruggingsobjectEntity = useAppSelector(state => state.overbruggingsobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="overbruggingsobjectDetailsHeading">Overbruggingsobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{overbruggingsobjectEntity.id}</dd>
          <dt>
            <span id="aanleghoogte">Aanleghoogte</span>
          </dt>
          <dd>{overbruggingsobjectEntity.aanleghoogte}</dd>
          <dt>
            <span id="antigraffitivoorziening">Antigraffitivoorziening</span>
          </dt>
          <dd>{overbruggingsobjectEntity.antigraffitivoorziening ? 'true' : 'false'}</dd>
          <dt>
            <span id="bereikbaarheid">Bereikbaarheid</span>
          </dt>
          <dd>{overbruggingsobjectEntity.bereikbaarheid}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{overbruggingsobjectEntity.breedte}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{overbruggingsobjectEntity.hoogte}</dd>
          <dt>
            <span id="installateur">Installateur</span>
          </dt>
          <dd>{overbruggingsobjectEntity.installateur}</dd>
          <dt>
            <span id="jaarconserveren">Jaarconserveren</span>
          </dt>
          <dd>{overbruggingsobjectEntity.jaarconserveren}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{overbruggingsobjectEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="jaarrenovatie">Jaarrenovatie</span>
          </dt>
          <dd>{overbruggingsobjectEntity.jaarrenovatie}</dd>
          <dt>
            <span id="jaarvervanging">Jaarvervanging</span>
          </dt>
          <dd>{overbruggingsobjectEntity.jaarvervanging}</dd>
          <dt>
            <span id="kleur">Kleur</span>
          </dt>
          <dd>{overbruggingsobjectEntity.kleur}</dd>
          <dt>
            <span id="kwaliteitsniveauactueel">Kwaliteitsniveauactueel</span>
          </dt>
          <dd>{overbruggingsobjectEntity.kwaliteitsniveauactueel}</dd>
          <dt>
            <span id="kwaliteitsniveaugewenst">Kwaliteitsniveaugewenst</span>
          </dt>
          <dd>{overbruggingsobjectEntity.kwaliteitsniveaugewenst}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{overbruggingsobjectEntity.lengte}</dd>
          <dt>
            <span id="looprichel">Looprichel</span>
          </dt>
          <dd>{overbruggingsobjectEntity.looprichel ? 'true' : 'false'}</dd>
          <dt>
            <span id="minimumconditiescore">Minimumconditiescore</span>
          </dt>
          <dd>{overbruggingsobjectEntity.minimumconditiescore}</dd>
          <dt>
            <span id="onderhoudsregime">Onderhoudsregime</span>
          </dt>
          <dd>{overbruggingsobjectEntity.onderhoudsregime}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{overbruggingsobjectEntity.oppervlakte}</dd>
          <dt>
            <span id="overbruggingsobjectmateriaal">Overbruggingsobjectmateriaal</span>
          </dt>
          <dd>{overbruggingsobjectEntity.overbruggingsobjectmateriaal}</dd>
          <dt>
            <span id="overbruggingsobjectmodaliteit">Overbruggingsobjectmodaliteit</span>
          </dt>
          <dd>{overbruggingsobjectEntity.overbruggingsobjectmodaliteit}</dd>
          <dt>
            <span id="technischelevensduur">Technischelevensduur</span>
          </dt>
          <dd>{overbruggingsobjectEntity.technischelevensduur}</dd>
          <dt>
            <span id="typefundering">Typefundering</span>
          </dt>
          <dd>{overbruggingsobjectEntity.typefundering}</dd>
          <dt>
            <span id="vervangingswaarde">Vervangingswaarde</span>
          </dt>
          <dd>{overbruggingsobjectEntity.vervangingswaarde}</dd>
        </dl>
        <Button tag={Link} to="/overbruggingsobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/overbruggingsobject/${overbruggingsobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OverbruggingsobjectDetail;
