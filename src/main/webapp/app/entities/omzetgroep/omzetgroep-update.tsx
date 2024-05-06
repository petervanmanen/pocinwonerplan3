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
import { IOmzetgroep } from 'app/shared/model/omzetgroep.model';
import { getEntity, updateEntity, createEntity, reset } from './omzetgroep.reducer';

export const OmzetgroepUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const products = useAppSelector(state => state.product.entities);
  const omzetgroepEntity = useAppSelector(state => state.omzetgroep.entity);
  const loading = useAppSelector(state => state.omzetgroep.loading);
  const updating = useAppSelector(state => state.omzetgroep.updating);
  const updateSuccess = useAppSelector(state => state.omzetgroep.updateSuccess);

  const handleClose = () => {
    navigate('/omzetgroep');
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
      ...omzetgroepEntity,
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
          ...omzetgroepEntity,
          valtbinnenProducts: omzetgroepEntity?.valtbinnenProducts?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.omzetgroep.home.createOrEditLabel" data-cy="OmzetgroepCreateUpdateHeading">
            Create or edit a Omzetgroep
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="omzetgroep-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="omzetgroep-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="omzetgroep-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Valtbinnen Product"
                id="omzetgroep-valtbinnenProduct"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/omzetgroep" replace color="info">
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

export default OmzetgroepUpdate;
