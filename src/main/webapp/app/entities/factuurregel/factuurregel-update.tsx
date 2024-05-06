import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMutatie } from 'app/shared/model/mutatie.model';
import { getEntities as getMutaties } from 'app/entities/mutatie/mutatie.reducer';
import { IFactuur } from 'app/shared/model/factuur.model';
import { getEntities as getFactuurs } from 'app/entities/factuur/factuur.reducer';
import { IFactuurregel } from 'app/shared/model/factuurregel.model';
import { getEntity, updateEntity, createEntity, reset } from './factuurregel.reducer';

export const FactuurregelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mutaties = useAppSelector(state => state.mutatie.entities);
  const factuurs = useAppSelector(state => state.factuur.entities);
  const factuurregelEntity = useAppSelector(state => state.factuurregel.entity);
  const loading = useAppSelector(state => state.factuurregel.loading);
  const updating = useAppSelector(state => state.factuurregel.updating);
  const updateSuccess = useAppSelector(state => state.factuurregel.updateSuccess);

  const handleClose = () => {
    navigate('/factuurregel');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMutaties({}));
    dispatch(getFactuurs({}));
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
    if (values.bedragbtw !== undefined && typeof values.bedragbtw !== 'number') {
      values.bedragbtw = Number(values.bedragbtw);
    }
    if (values.bedragexbtw !== undefined && typeof values.bedragexbtw !== 'number') {
      values.bedragexbtw = Number(values.bedragexbtw);
    }

    const entity = {
      ...factuurregelEntity,
      ...values,
      leidttotMutatie: mutaties.find(it => it.id.toString() === values.leidttotMutatie?.toString()),
      heeftFactuur: factuurs.find(it => it.id.toString() === values.heeftFactuur?.toString()),
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
          ...factuurregelEntity,
          leidttotMutatie: factuurregelEntity?.leidttotMutatie?.id,
          heeftFactuur: factuurregelEntity?.heeftFactuur?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.factuurregel.home.createOrEditLabel" data-cy="FactuurregelCreateUpdateHeading">
            Create or edit a Factuurregel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="factuurregel-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aantal" id="factuurregel-aantal" name="aantal" data-cy="aantal" type="text" />
              <ValidatedField label="Bedragbtw" id="factuurregel-bedragbtw" name="bedragbtw" data-cy="bedragbtw" type="text" />
              <ValidatedField label="Bedragexbtw" id="factuurregel-bedragexbtw" name="bedragexbtw" data-cy="bedragexbtw" type="text" />
              <ValidatedField
                label="Btwpercentage"
                id="factuurregel-btwpercentage"
                name="btwpercentage"
                data-cy="btwpercentage"
                type="text"
              />
              <ValidatedField label="Nummer" id="factuurregel-nummer" name="nummer" data-cy="nummer" type="text" />
              <ValidatedField label="Omschrijving" id="factuurregel-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="factuurregel-leidttotMutatie"
                name="leidttotMutatie"
                data-cy="leidttotMutatie"
                label="Leidttot Mutatie"
                type="select"
              >
                <option value="" key="0" />
                {mutaties
                  ? mutaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="factuurregel-heeftFactuur"
                name="heeftFactuur"
                data-cy="heeftFactuur"
                label="Heeft Factuur"
                type="select"
                required
              >
                <option value="" key="0" />
                {factuurs
                  ? factuurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/factuurregel" replace color="info">
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

export default FactuurregelUpdate;
