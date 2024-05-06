import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPrijs } from 'app/shared/model/prijs.model';
import { getEntities as getPrijs } from 'app/entities/prijs/prijs.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IBalieverkoop } from 'app/shared/model/balieverkoop.model';
import { getEntity, updateEntity, createEntity, reset } from './balieverkoop.reducer';

export const BalieverkoopUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const prijs = useAppSelector(state => state.prijs.entities);
  const products = useAppSelector(state => state.product.entities);
  const balieverkoopEntity = useAppSelector(state => state.balieverkoop.entity);
  const loading = useAppSelector(state => state.balieverkoop.loading);
  const updating = useAppSelector(state => state.balieverkoop.updating);
  const updateSuccess = useAppSelector(state => state.balieverkoop.updateSuccess);

  const handleClose = () => {
    navigate('/balieverkoop');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPrijs({}));
    dispatch(getProducts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...balieverkoopEntity,
      ...values,
      tegenprijsPrijs: prijs.find(it => it.id.toString() === values.tegenprijsPrijs?.toString()),
      betreftProduct: products.find(it => it.id.toString() === values.betreftProduct?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...balieverkoopEntity,
          tegenprijsPrijs: balieverkoopEntity?.tegenprijsPrijs?.id,
          betreftProduct: balieverkoopEntity?.betreftProduct?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.balieverkoop.home.createOrEditLabel" data-cy="BalieverkoopCreateUpdateHeading">
            Create or edit a Balieverkoop
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="balieverkoop-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aantal" id="balieverkoop-aantal" name="aantal" data-cy="aantal" type="text" />
              <ValidatedField label="Kanaal" id="balieverkoop-kanaal" name="kanaal" data-cy="kanaal" type="text" />
              <ValidatedField label="Verkooptijd" id="balieverkoop-verkooptijd" name="verkooptijd" data-cy="verkooptijd" type="text" />
              <ValidatedField
                id="balieverkoop-tegenprijsPrijs"
                name="tegenprijsPrijs"
                data-cy="tegenprijsPrijs"
                label="Tegenprijs Prijs"
                type="select"
              >
                <option value="" key="0" />
                {prijs
                  ? prijs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="balieverkoop-betreftProduct"
                name="betreftProduct"
                data-cy="betreftProduct"
                label="Betreft Product"
                type="select"
              >
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/balieverkoop" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BalieverkoopUpdate;
