import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Overlijdeningeschrevennatuurlijkpersoon from './overlijdeningeschrevennatuurlijkpersoon';
import OverlijdeningeschrevennatuurlijkpersoonDetail from './overlijdeningeschrevennatuurlijkpersoon-detail';
import OverlijdeningeschrevennatuurlijkpersoonUpdate from './overlijdeningeschrevennatuurlijkpersoon-update';
import OverlijdeningeschrevennatuurlijkpersoonDeleteDialog from './overlijdeningeschrevennatuurlijkpersoon-delete-dialog';

const OverlijdeningeschrevennatuurlijkpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Overlijdeningeschrevennatuurlijkpersoon />} />
    <Route path="new" element={<OverlijdeningeschrevennatuurlijkpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<OverlijdeningeschrevennatuurlijkpersoonDetail />} />
      <Route path="edit" element={<OverlijdeningeschrevennatuurlijkpersoonUpdate />} />
      <Route path="delete" element={<OverlijdeningeschrevennatuurlijkpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OverlijdeningeschrevennatuurlijkpersoonRoutes;
