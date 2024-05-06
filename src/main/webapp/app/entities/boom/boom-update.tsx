import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBoom } from 'app/shared/model/boom.model';
import { getEntity, updateEntity, createEntity, reset } from './boom.reducer';

export const BoomUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const boomEntity = useAppSelector(state => state.boom.entity);
  const loading = useAppSelector(state => state.boom.loading);
  const updating = useAppSelector(state => state.boom.updating);
  const updateSuccess = useAppSelector(state => state.boom.updateSuccess);

  const handleClose = () => {
    navigate('/boom');
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
      ...boomEntity,
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
          ...boomEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.boom.home.createOrEditLabel" data-cy="BoomCreateUpdateHeading">
            Create or edit a Boom
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="boom-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Beleidsstatus" id="boom-beleidsstatus" name="beleidsstatus" data-cy="beleidsstatus" type="text" />
              <ValidatedField
                label="Beoogdeomlooptijd"
                id="boom-beoogdeomlooptijd"
                name="beoogdeomlooptijd"
                data-cy="beoogdeomlooptijd"
                type="text"
              />
              <ValidatedField label="Boombeeld" id="boom-boombeeld" name="boombeeld" data-cy="boombeeld" type="text" />
              <ValidatedField label="Boombeschermer" id="boom-boombeschermer" name="boombeschermer" data-cy="boombeschermer" type="text" />
              <ValidatedField label="Boomgroep" id="boom-boomgroep" name="boomgroep" data-cy="boomgroep" type="text" />
              <ValidatedField
                label="Boomhoogteactueel"
                id="boom-boomhoogteactueel"
                name="boomhoogteactueel"
                data-cy="boomhoogteactueel"
                type="text"
              />
              <ValidatedField
                label="Boomhoogteklasseactueel"
                id="boom-boomhoogteklasseactueel"
                name="boomhoogteklasseactueel"
                data-cy="boomhoogteklasseactueel"
                type="text"
              />
              <ValidatedField
                label="Boomhoogteklasseeindebeeld"
                id="boom-boomhoogteklasseeindebeeld"
                name="boomhoogteklasseeindebeeld"
                data-cy="boomhoogteklasseeindebeeld"
                type="text"
              />
              <ValidatedField label="Boomspiegel" id="boom-boomspiegel" name="boomspiegel" data-cy="boomspiegel" type="text" />
              <ValidatedField
                label="Boomtypebeschermingsstatusplus"
                id="boom-boomtypebeschermingsstatusplus"
                name="boomtypebeschermingsstatusplus"
                data-cy="boomtypebeschermingsstatusplus"
                type="text"
              />
              <ValidatedField
                label="Boomvoorziening"
                id="boom-boomvoorziening"
                name="boomvoorziening"
                data-cy="boomvoorziening"
                type="text"
              />
              <ValidatedField
                label="Controlefrequentie"
                id="boom-controlefrequentie"
                name="controlefrequentie"
                data-cy="controlefrequentie"
                type="text"
              />
              <ValidatedField
                label="Feestverlichting"
                id="boom-feestverlichting"
                name="feestverlichting"
                data-cy="feestverlichting"
                type="text"
              />
              <ValidatedField label="Groeifase" id="boom-groeifase" name="groeifase" data-cy="groeifase" type="text" />
              <ValidatedField
                label="Groeiplaatsinrichting"
                id="boom-groeiplaatsinrichting"
                name="groeiplaatsinrichting"
                data-cy="groeiplaatsinrichting"
                type="text"
              />
              <ValidatedField
                label="Herplantplicht"
                id="boom-herplantplicht"
                name="herplantplicht"
                data-cy="herplantplicht"
                check
                type="checkbox"
              />
              <ValidatedField label="Kiemjaar" id="boom-kiemjaar" name="kiemjaar" data-cy="kiemjaar" type="text" />
              <ValidatedField
                label="Kroondiameterklasseactueel"
                id="boom-kroondiameterklasseactueel"
                name="kroondiameterklasseactueel"
                data-cy="kroondiameterklasseactueel"
                type="text"
              />
              <ValidatedField
                label="Kroondiameterklasseeindebeeld"
                id="boom-kroondiameterklasseeindebeeld"
                name="kroondiameterklasseeindebeeld"
                data-cy="kroondiameterklasseeindebeeld"
                type="text"
              />
              <ValidatedField label="Kroonvolume" id="boom-kroonvolume" name="kroonvolume" data-cy="kroonvolume" type="text" />
              <ValidatedField label="Leeftijd" id="boom-leeftijd" name="leeftijd" data-cy="leeftijd" type="text" />
              <ValidatedField label="Meerstammig" id="boom-meerstammig" name="meerstammig" data-cy="meerstammig" check type="checkbox" />
              <ValidatedField
                label="Monetaireboomwaarde"
                id="boom-monetaireboomwaarde"
                name="monetaireboomwaarde"
                data-cy="monetaireboomwaarde"
                type="text"
              />
              <ValidatedField label="Snoeifase" id="boom-snoeifase" name="snoeifase" data-cy="snoeifase" type="text" />
              <ValidatedField label="Stamdiameter" id="boom-stamdiameter" name="stamdiameter" data-cy="stamdiameter" type="text" />
              <ValidatedField
                label="Stamdiameterklasse"
                id="boom-stamdiameterklasse"
                name="stamdiameterklasse"
                data-cy="stamdiameterklasse"
                type="text"
              />
              <ValidatedField
                label="Takvrijeruimtetotgebouw"
                id="boom-takvrijeruimtetotgebouw"
                name="takvrijeruimtetotgebouw"
                data-cy="takvrijeruimtetotgebouw"
                type="text"
              />
              <ValidatedField label="Takvrijestam" id="boom-takvrijestam" name="takvrijestam" data-cy="takvrijestam" type="text" />
              <ValidatedField
                label="Takvrijezoneprimair"
                id="boom-takvrijezoneprimair"
                name="takvrijezoneprimair"
                data-cy="takvrijezoneprimair"
                type="text"
              />
              <ValidatedField
                label="Takvrijezonesecundair"
                id="boom-takvrijezonesecundair"
                name="takvrijezonesecundair"
                data-cy="takvrijezonesecundair"
                type="text"
              />
              <ValidatedField label="Transponder" id="boom-transponder" name="transponder" data-cy="transponder" type="text" />
              <ValidatedField label="Type" id="boom-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Typebeschermingsstatus"
                id="boom-typebeschermingsstatus"
                name="typebeschermingsstatus"
                data-cy="typebeschermingsstatus"
                type="text"
              />
              <ValidatedField
                label="Typeomgevingsrisicoklasse"
                id="boom-typeomgevingsrisicoklasse"
                name="typeomgevingsrisicoklasse"
                data-cy="typeomgevingsrisicoklasse"
                type="text"
              />
              <ValidatedField label="Typeplus" id="boom-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <ValidatedField
                label="Typevermeerderingsvorm"
                id="boom-typevermeerderingsvorm"
                name="typevermeerderingsvorm"
                data-cy="typevermeerderingsvorm"
                type="text"
              />
              <ValidatedField
                label="Veiligheidsklasseboom"
                id="boom-veiligheidsklasseboom"
                name="veiligheidsklasseboom"
                data-cy="veiligheidsklasseboom"
                type="text"
              />
              <ValidatedField label="Verplant" id="boom-verplant" name="verplant" data-cy="verplant" check type="checkbox" />
              <ValidatedField
                label="Verplantbaar"
                id="boom-verplantbaar"
                name="verplantbaar"
                data-cy="verplantbaar"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Vrijedoorrijhoogte"
                id="boom-vrijedoorrijhoogte"
                name="vrijedoorrijhoogte"
                data-cy="vrijedoorrijhoogte"
                type="text"
              />
              <ValidatedField
                label="Vrijedoorrijhoogteprimair"
                id="boom-vrijedoorrijhoogteprimair"
                name="vrijedoorrijhoogteprimair"
                data-cy="vrijedoorrijhoogteprimair"
                type="text"
              />
              <ValidatedField
                label="Vrijedoorrijhoogtesecundair"
                id="boom-vrijedoorrijhoogtesecundair"
                name="vrijedoorrijhoogtesecundair"
                data-cy="vrijedoorrijhoogtesecundair"
                type="text"
              />
              <ValidatedField label="Vrijetakval" id="boom-vrijetakval" name="vrijetakval" data-cy="vrijetakval" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/boom" replace color="info">
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

export default BoomUpdate;
