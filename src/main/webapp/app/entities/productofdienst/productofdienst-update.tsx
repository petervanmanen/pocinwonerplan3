import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProductofdienst } from 'app/shared/model/productofdienst.model';
import { getEntity, updateEntity, createEntity, reset } from './productofdienst.reducer';

export const ProductofdienstUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const productofdienstEntity = useAppSelector(state => state.productofdienst.entity);
  const loading = useAppSelector(state => state.productofdienst.loading);
  const updating = useAppSelector(state => state.productofdienst.updating);
  const updateSuccess = useAppSelector(state => state.productofdienst.updateSuccess);

  const handleClose = () => {
    navigate('/productofdienst');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...productofdienstEntity,
      ...values,
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
          ...productofdienstEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.productofdienst.home.createOrEditLabel" data-cy="ProductofdienstCreateUpdateHeading">
            Create or edit a Productofdienst
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="productofdienst-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Afhandeltijd"
                id="productofdienst-afhandeltijd"
                name="afhandeltijd"
                data-cy="afhandeltijd"
                type="text"
              />
              <ValidatedField label="Ingebruik" id="productofdienst-ingebruik" name="ingebruik" data-cy="ingebruik" type="text" />
              <ValidatedField label="Naam" id="productofdienst-naam" name="naam" data-cy="naam" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/productofdienst" replace color="info">
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

export default ProductofdienstUpdate;
