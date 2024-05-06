import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IPrijs } from 'app/shared/model/prijs.model';
import { getEntity, updateEntity, createEntity, reset } from './prijs.reducer';

export const PrijsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const products = useAppSelector(state => state.product.entities);
  const prijsEntity = useAppSelector(state => state.prijs.entity);
  const loading = useAppSelector(state => state.prijs.loading);
  const updating = useAppSelector(state => state.prijs.updating);
  const updateSuccess = useAppSelector(state => state.prijs.updateSuccess);

  const handleClose = () => {
    navigate('/prijs');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
    if (values.bedrag !== undefined && typeof values.bedrag !== 'number') {
      values.bedrag = Number(values.bedrag);
    }

    const entity = {
      ...prijsEntity,
      ...values,
      heeftprijsProduct: products.find(it => it.id.toString() === values.heeftprijsProduct?.toString()),
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
          ...prijsEntity,
          heeftprijsProduct: prijsEntity?.heeftprijsProduct?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.prijs.home.createOrEditLabel" data-cy="PrijsCreateUpdateHeading">
            Create or edit a Prijs
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="prijs-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Bedrag" id="prijs-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="prijs-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField label="Datumstart" id="prijs-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField
                id="prijs-heeftprijsProduct"
                name="heeftprijsProduct"
                data-cy="heeftprijsProduct"
                label="Heeftprijs Product"
                type="select"
                required
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
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/prijs" replace color="info">
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

export default PrijsUpdate;
