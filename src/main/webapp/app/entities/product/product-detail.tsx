import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product.reducer';

export const ProductDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productEntity = useAppSelector(state => state.product.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDetailsHeading">Product</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productEntity.id}</dd>
          <dt>
            <span id="codemuseumjaarkaart">Codemuseumjaarkaart</span>
          </dt>
          <dd>{productEntity.codemuseumjaarkaart}</dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {productEntity.datumeindegeldigheid ? (
              <TextFormat value={productEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {productEntity.datumstart ? <TextFormat value={productEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="entreekaart">Entreekaart</span>
          </dt>
          <dd>{productEntity.entreekaart}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{productEntity.omschrijving}</dd>
          <dt>
            <span id="prijs">Prijs</span>
          </dt>
          <dd>{productEntity.prijs}</dd>
          <dt>Leverancier Leverancier</dt>
          <dd>{productEntity.leverancierLeverancier ? productEntity.leverancierLeverancier.id : ''}</dd>
          <dt>Heeft Kostenplaats</dt>
          <dd>{productEntity.heeftKostenplaats ? productEntity.heeftKostenplaats.id : ''}</dd>
          <dt>Valtbinnen Omzetgroep</dt>
          <dd>
            {productEntity.valtbinnenOmzetgroeps
              ? productEntity.valtbinnenOmzetgroeps.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {productEntity.valtbinnenOmzetgroeps && i === productEntity.valtbinnenOmzetgroeps.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Valtbinnen Productgroep</dt>
          <dd>
            {productEntity.valtbinnenProductgroeps
              ? productEntity.valtbinnenProductgroeps.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {productEntity.valtbinnenProductgroeps && i === productEntity.valtbinnenProductgroeps.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Doelstelling</dt>
          <dd>{productEntity.heeftDoelstelling ? productEntity.heeftDoelstelling.id : ''}</dd>
          <dt>Isopdrachtgever Opdrachtgever</dt>
          <dd>{productEntity.isopdrachtgeverOpdrachtgever ? productEntity.isopdrachtgeverOpdrachtgever.id : ''}</dd>
          <dt>Isopdrachtnemer Opdrachtnemer</dt>
          <dd>{productEntity.isopdrachtnemerOpdrachtnemer ? productEntity.isopdrachtnemerOpdrachtnemer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductDetail;
