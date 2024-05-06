import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kunstwerk.reducer';

export const KunstwerkDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kunstwerkEntity = useAppSelector(state => state.kunstwerk.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kunstwerkDetailsHeading">Kunstwerk</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kunstwerkEntity.id}</dd>
          <dt>
            <span id="aanleghoogte">Aanleghoogte</span>
          </dt>
          <dd>{kunstwerkEntity.aanleghoogte}</dd>
          <dt>
            <span id="antigraffitivoorziening">Antigraffitivoorziening</span>
          </dt>
          <dd>{kunstwerkEntity.antigraffitivoorziening ? 'true' : 'false'}</dd>
          <dt>
            <span id="bereikbaarheid">Bereikbaarheid</span>
          </dt>
          <dd>{kunstwerkEntity.bereikbaarheid}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{kunstwerkEntity.breedte}</dd>
          <dt>
            <span id="constructietype">Constructietype</span>
          </dt>
          <dd>{kunstwerkEntity.constructietype}</dd>
          <dt>
            <span id="gewicht">Gewicht</span>
          </dt>
          <dd>{kunstwerkEntity.gewicht}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{kunstwerkEntity.hoogte}</dd>
          <dt>
            <span id="installateur">Installateur</span>
          </dt>
          <dd>{kunstwerkEntity.installateur}</dd>
          <dt>
            <span id="jaarconserveren">Jaarconserveren</span>
          </dt>
          <dd>{kunstwerkEntity.jaarconserveren}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{kunstwerkEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="jaarrenovatie">Jaarrenovatie</span>
          </dt>
          <dd>{kunstwerkEntity.jaarrenovatie}</dd>
          <dt>
            <span id="jaarvervanging">Jaarvervanging</span>
          </dt>
          <dd>{kunstwerkEntity.jaarvervanging}</dd>
          <dt>
            <span id="kilometreringbegin">Kilometreringbegin</span>
          </dt>
          <dd>{kunstwerkEntity.kilometreringbegin}</dd>
          <dt>
            <span id="kilometreringeinde">Kilometreringeinde</span>
          </dt>
          <dd>{kunstwerkEntity.kilometreringeinde}</dd>
          <dt>
            <span id="kleur">Kleur</span>
          </dt>
          <dd>{kunstwerkEntity.kleur}</dd>
          <dt>
            <span id="kunstwerkbereikbaarheidplus">Kunstwerkbereikbaarheidplus</span>
          </dt>
          <dd>{kunstwerkEntity.kunstwerkbereikbaarheidplus}</dd>
          <dt>
            <span id="kunstwerkmateriaal">Kunstwerkmateriaal</span>
          </dt>
          <dd>{kunstwerkEntity.kunstwerkmateriaal}</dd>
          <dt>
            <span id="kwaliteitsniveauactueel">Kwaliteitsniveauactueel</span>
          </dt>
          <dd>{kunstwerkEntity.kwaliteitsniveauactueel}</dd>
          <dt>
            <span id="kwaliteitsniveaugewenst">Kwaliteitsniveaugewenst</span>
          </dt>
          <dd>{kunstwerkEntity.kwaliteitsniveaugewenst}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{kunstwerkEntity.lengte}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{kunstwerkEntity.leverancier}</dd>
          <dt>
            <span id="looprichel">Looprichel</span>
          </dt>
          <dd>{kunstwerkEntity.looprichel ? 'true' : 'false'}</dd>
          <dt>
            <span id="minimumconditiescore">Minimumconditiescore</span>
          </dt>
          <dd>{kunstwerkEntity.minimumconditiescore}</dd>
          <dt>
            <span id="monument">Monument</span>
          </dt>
          <dd>{kunstwerkEntity.monument ? 'true' : 'false'}</dd>
          <dt>
            <span id="monumentnummer">Monumentnummer</span>
          </dt>
          <dd>{kunstwerkEntity.monumentnummer}</dd>
          <dt>
            <span id="eobjectnaam">Eobjectnaam</span>
          </dt>
          <dd>{kunstwerkEntity.eobjectnaam}</dd>
          <dt>
            <span id="eobjectnummer">Eobjectnummer</span>
          </dt>
          <dd>{kunstwerkEntity.eobjectnummer}</dd>
          <dt>
            <span id="onderhoudsregime">Onderhoudsregime</span>
          </dt>
          <dd>{kunstwerkEntity.onderhoudsregime}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{kunstwerkEntity.oppervlakte}</dd>
          <dt>
            <span id="orientatie">Orientatie</span>
          </dt>
          <dd>{kunstwerkEntity.orientatie}</dd>
          <dt>
            <span id="technischelevensduur">Technischelevensduur</span>
          </dt>
          <dd>{kunstwerkEntity.technischelevensduur}</dd>
          <dt>
            <span id="typefundering">Typefundering</span>
          </dt>
          <dd>{kunstwerkEntity.typefundering}</dd>
          <dt>
            <span id="typemonument">Typemonument</span>
          </dt>
          <dd>{kunstwerkEntity.typemonument}</dd>
          <dt>
            <span id="vervangingswaarde">Vervangingswaarde</span>
          </dt>
          <dd>{kunstwerkEntity.vervangingswaarde}</dd>
          <dt>
            <span id="wegnummer">Wegnummer</span>
          </dt>
          <dd>{kunstwerkEntity.wegnummer}</dd>
        </dl>
        <Button tag={Link} to="/kunstwerk" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kunstwerk/${kunstwerkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KunstwerkDetail;
