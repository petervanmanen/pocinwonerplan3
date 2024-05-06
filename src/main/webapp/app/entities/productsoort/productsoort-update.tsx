import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProductgroep } from 'app/shared/model/productgroep.model';
import { getEntities as getProductgroeps } from 'app/entities/productgroep/productgroep.reducer';
import { IProductsoort } from 'app/shared/model/productsoort.model';
import { getEntity, updateEntity, createEntity, reset } from './productsoort.reducer';

export const ProductsoortUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const productgroeps = useAppSelector(state => state.productgroep.entities);
  const productsoortEntity = useAppSelector(state => state.productsoort.entity);
  const loading = useAppSelector(state => state.productsoort.loading);
  const updating = useAppSelector(state => state.productsoort.updating);
  const updateSuccess = useAppSelector(state => state.productsoort.updateSuccess);

  const handleClose = () => {
    navigate('/productsoort');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProductgroeps({}));
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
    if (values.tarief !== undefined && typeof values.tarief !== 'number') {
      values.tarief = Number(values.tarief);
    }

    const entity = {
      ...productsoortEntity,
      ...values,
      valtbinnenProductgroep: productgroeps.find(it => it.id.toString() === values.valtbinnenProductgroep?.toString()),
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
          ...productsoortEntity,
          valtbinnenProductgroep: productsoortEntity?.valtbinnenProductgroep?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.productsoort.home.createOrEditLabel" data-cy="ProductsoortCreateUpdateHeading">
            Create or edit a Productsoort
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="productsoort-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Code" id="productsoort-code" name="code" data-cy="code" type="text" />
              <ValidatedField label="Omschrijving" id="productsoort-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Tarief" id="productsoort-tarief" name="tarief" data-cy="tarief" type="text" />
              <ValidatedField
                label="Tariefperiode"
                id="productsoort-tariefperiode"
                name="tariefperiode"
                data-cy="tariefperiode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                id="productsoort-valtbinnenProductgroep"
                name="valtbinnenProductgroep"
                data-cy="valtbinnenProductgroep"
                label="Valtbinnen Productgroep"
                type="select"
              >
                <option value="" key="0" />
                {productgroeps
                  ? productgroeps.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/productsoort" replace color="info">
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

export default ProductsoortUpdate;
