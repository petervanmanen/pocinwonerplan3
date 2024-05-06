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
import { IProductgroep } from 'app/shared/model/productgroep.model';
import { getEntity, updateEntity, createEntity, reset } from './productgroep.reducer';

export const ProductgroepUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const products = useAppSelector(state => state.product.entities);
  const productgroepEntity = useAppSelector(state => state.productgroep.entity);
  const loading = useAppSelector(state => state.productgroep.loading);
  const updating = useAppSelector(state => state.productgroep.updating);
  const updateSuccess = useAppSelector(state => state.productgroep.updateSuccess);

  const handleClose = () => {
    navigate('/productgroep');
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

    const entity = {
      ...productgroepEntity,
      ...values,
      valtbinnenProducts: mapIdList(values.valtbinnenProducts),
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
          ...productgroepEntity,
          valtbinnenProducts: productgroepEntity?.valtbinnenProducts?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.productgroep.home.createOrEditLabel" data-cy="ProductgroepCreateUpdateHeading">
            Create or edit a Productgroep
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="productgroep-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Beslisboom" id="productgroep-beslisboom" name="beslisboom" data-cy="beslisboom" type="text" />
              <ValidatedField label="Code" id="productgroep-code" name="code" data-cy="code" type="text" />
              <ValidatedField label="Omschrijving" id="productgroep-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Valtbinnen Product"
                id="productgroep-valtbinnenProduct"
                data-cy="valtbinnenProduct"
                type="select"
                multiple
                name="valtbinnenProducts"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/productgroep" replace color="info">
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

export default ProductgroepUpdate;
