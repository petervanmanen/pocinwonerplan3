import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFunctie } from 'app/shared/model/functie.model';
import { getEntities as getFuncties } from 'app/entities/functie/functie.reducer';
import { IVacature } from 'app/shared/model/vacature.model';
import { getEntity, updateEntity, createEntity, reset } from './vacature.reducer';

export const VacatureUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const functies = useAppSelector(state => state.functie.entities);
  const vacatureEntity = useAppSelector(state => state.vacature.entity);
  const loading = useAppSelector(state => state.vacature.loading);
  const updating = useAppSelector(state => state.vacature.updating);
  const updateSuccess = useAppSelector(state => state.vacature.updateSuccess);

  const handleClose = () => {
    navigate('/vacature');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFuncties({}));
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
      ...vacatureEntity,
      ...values,
      vacaturebijfunctieFunctie: functies.find(it => it.id.toString() === values.vacaturebijfunctieFunctie?.toString()),
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
          ...vacatureEntity,
          vacaturebijfunctieFunctie: vacatureEntity?.vacaturebijfunctieFunctie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vacature.home.createOrEditLabel" data-cy="VacatureCreateUpdateHeading">
            Create or edit a Vacature
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vacature-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datumgesloten" id="vacature-datumgesloten" name="datumgesloten" data-cy="datumgesloten" type="date" />
              <ValidatedField
                label="Datumopengesteld"
                id="vacature-datumopengesteld"
                name="datumopengesteld"
                data-cy="datumopengesteld"
                type="text"
              />
              <ValidatedField label="Deeltijd" id="vacature-deeltijd" name="deeltijd" data-cy="deeltijd" check type="checkbox" />
              <ValidatedField label="Extern" id="vacature-extern" name="extern" data-cy="extern" check type="checkbox" />
              <ValidatedField label="Intern" id="vacature-intern" name="intern" data-cy="intern" check type="checkbox" />
              <ValidatedField
                label="Vastedienst"
                id="vacature-vastedienst"
                name="vastedienst"
                data-cy="vastedienst"
                check
                type="checkbox"
              />
              <ValidatedField
                id="vacature-vacaturebijfunctieFunctie"
                name="vacaturebijfunctieFunctie"
                data-cy="vacaturebijfunctieFunctie"
                label="Vacaturebijfunctie Functie"
                type="select"
              >
                <option value="" key="0" />
                {functies
                  ? functies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vacature" replace color="info">
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

export default VacatureUpdate;
