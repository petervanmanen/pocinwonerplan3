import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Overlijdeningeschrevenpersoon from './overlijdeningeschrevenpersoon';
import OverlijdeningeschrevenpersoonDetail from './overlijdeningeschrevenpersoon-detail';
import OverlijdeningeschrevenpersoonUpdate from './overlijdeningeschrevenpersoon-update';
import OverlijdeningeschrevenpersoonDeleteDialog from './overlijdeningeschrevenpersoon-delete-dialog';

const OverlijdeningeschrevenpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Overlijdeningeschrevenpersoon />} />
    <Route path="new" element={<OverlijdeningeschrevenpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<OverlijdeningeschrevenpersoonDetail />} />
      <Route path="edit" element={<OverlijdeningeschrevenpersoonUpdate />} />
      <Route path="delete" element={<OverlijdeningeschrevenpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OverlijdeningeschrevenpersoonRoutes;
