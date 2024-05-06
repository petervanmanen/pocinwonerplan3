import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWaterobject } from 'app/shared/model/waterobject.model';
import { getEntity, updateEntity, createEntity, reset } from './waterobject.reducer';

export const WaterobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const waterobjectEntity = useAppSelector(state => state.waterobject.entity);
  const loading = useAppSelector(state => state.waterobject.loading);
  const updating = useAppSelector(state => state.waterobject.updating);
  const updateSuccess = useAppSelector(state => state.waterobject.updateSuccess);

  const handleClose = () => {
    navigate('/waterobject');
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
      ...waterobjectEntity,
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
          ...waterobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.waterobject.home.createOrEditLabel" data-cy="WaterobjectCreateUpdateHeading">
            Create or edit a Waterobject
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="waterobject-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Breedte" id="waterobject-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField label="Folie" id="waterobject-folie" name="folie" data-cy="folie" check type="checkbox" />
              <ValidatedField label="Hoogte" id="waterobject-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Infiltrerendoppervlak"
                id="waterobject-infiltrerendoppervlak"
                name="infiltrerendoppervlak"
                data-cy="infiltrerendoppervlak"
                type="text"
              />
              <ValidatedField
                label="Infiltrerendvermogen"
                id="waterobject-infiltrerendvermogen"
                name="infiltrerendvermogen"
                data-cy="infiltrerendvermogen"
                type="text"
              />
              <ValidatedField label="Lengte" id="waterobject-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Lozingspunt" id="waterobject-lozingspunt" name="lozingspunt" data-cy="lozingspunt" type="text" />
              <ValidatedField label="Oppervlakte" id="waterobject-oppervlakte" name="oppervlakte" data-cy="oppervlakte" type="text" />
              <ValidatedField label="Porositeit" id="waterobject-porositeit" name="porositeit" data-cy="porositeit" type="text" />
              <ValidatedField label="Streefdiepte" id="waterobject-streefdiepte" name="streefdiepte" data-cy="streefdiepte" type="text" />
              <ValidatedField label="Type" id="waterobject-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Typeplus" id="waterobject-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <ValidatedField
                label="Typeplus 2"
                id="waterobject-typeplus2"
                name="typeplus2"
                data-cy="typeplus2"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Typevaarwater"
                id="waterobject-typevaarwater"
                name="typevaarwater"
                data-cy="typevaarwater"
                type="text"
              />
              <ValidatedField
                label="Typewaterplant"
                id="waterobject-typewaterplant"
                name="typewaterplant"
                data-cy="typewaterplant"
                type="text"
              />
              <ValidatedField
                label="Uitstroomniveau"
                id="waterobject-uitstroomniveau"
                name="uitstroomniveau"
                data-cy="uitstroomniveau"
                type="text"
              />
              <ValidatedField
                label="Vaarwegtraject"
                id="waterobject-vaarwegtraject"
                name="vaarwegtraject"
                data-cy="vaarwegtraject"
                type="text"
              />
              <ValidatedField label="Vorm" id="waterobject-vorm" name="vorm" data-cy="vorm" type="text" />
              <ValidatedField
                label="Waternaam"
                id="waterobject-waternaam"
                name="waternaam"
                data-cy="waternaam"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField label="Waterpeil" id="waterobject-waterpeil" name="waterpeil" data-cy="waterpeil" type="text" />
              <ValidatedField
                label="Waterpeilwinter"
                id="waterobject-waterpeilwinter"
                name="waterpeilwinter"
                data-cy="waterpeilwinter"
                type="text"
              />
              <ValidatedField
                label="Waterpeilzomer"
                id="waterobject-waterpeilzomer"
                name="waterpeilzomer"
                data-cy="waterpeilzomer"
                type="text"
              />
              <ValidatedField
                label="Waterplanten"
                id="waterobject-waterplanten"
                name="waterplanten"
                data-cy="waterplanten"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/waterobject" replace color="info">
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

export default WaterobjectUpdate;
