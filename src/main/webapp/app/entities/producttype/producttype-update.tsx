import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBedrijfsprocestype } from 'app/shared/model/bedrijfsprocestype.model';
import { getEntities as getBedrijfsprocestypes } from 'app/entities/bedrijfsprocestype/bedrijfsprocestype.reducer';
import { IProducttype } from 'app/shared/model/producttype.model';
import { getEntity, updateEntity, createEntity, reset } from './producttype.reducer';

export const ProducttypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bedrijfsprocestypes = useAppSelector(state => state.bedrijfsprocestype.entities);
  const producttypeEntity = useAppSelector(state => state.producttype.entity);
  const loading = useAppSelector(state => state.producttype.loading);
  const updating = useAppSelector(state => state.producttype.updating);
  const updateSuccess = useAppSelector(state => state.producttype.updateSuccess);

  const handleClose = () => {
    navigate('/producttype');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBedrijfsprocestypes({}));
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
      ...producttypeEntity,
      ...values,
      heeftBedrijfsprocestype: bedrijfsprocestypes.find(it => it.id.toString() === values.heeftBedrijfsprocestype?.toString()),
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
          ...producttypeEntity,
          heeftBedrijfsprocestype: producttypeEntity?.heeftBedrijfsprocestype?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.producttype.home.createOrEditLabel" data-cy="ProducttypeCreateUpdateHeading">
            Create or edit a Producttype
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="producttype-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Omschrijving" id="producttype-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="producttype-heeftBedrijfsprocestype"
                name="heeftBedrijfsprocestype"
                data-cy="heeftBedrijfsprocestype"
                label="Heeft Bedrijfsprocestype"
                type="select"
                required
              >
                <option value="" key="0" />
                {bedrijfsprocestypes
                  ? bedrijfsprocestypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/producttype" replace color="info">
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

export default ProducttypeUpdate;
